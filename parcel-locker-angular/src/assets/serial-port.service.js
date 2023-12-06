let uniqueCourierId;

let selectedPort;

//Kapocsolódás az arduino-hoz
//Az arduino adatai
//usbProductId: 67, usbVendorId: 9025
const connectToArduino = async () => {

    //Az oldal frissítése után a selectedPort értéke null lesz
    //Ha null, akkor előbb újra kapcsolódni kell az arduino-hoz
    if (selectedPort == null) {
        //Összes eltárolt soros eszköz lekérése, akihez már csatlakoztam korábban
        const ports = await navigator.serial.getPorts();
        //Arudino kiválasztása csatlakozáshoz
        const selected = ports.find(port => port.getInfo().usbProductId === 67);
        //Soros kapcsolat megnyitása
        await selected.open({ baudRate: 9600 });
        //Globális soros kapcsolati objektumnak itt adok értéket
        selectedPort = selected;
    }

}

const printPort = async () => {

    console.log(selectedPort);

    //Összes port kiiratása
    const ports = await navigator.serial.getPorts();
    ports.find(port => console.log(port.getInfo()));
    console.log("Ez az első: " + ports[0].getInfo().usbProductId);

}


//Régi soros olvasás
//Itt még nem használtam ki a soros buffer nyújtotta előnyöket
//A while ciklussal figyeltem a soros portot, és vártam az adatokat. Amikor a futár rákattintott a bejelentkezés menüpontra
//Ha megvolt a 10 karakter hosszú rfid azonosító, akkor abbahagytam az olvasást. Amíg nem volt meg, addig figyeltem a soros portot,
//és vártam az adatokat
const oldSerialRead = async () => {
    let uId = ""
    let counter = 0;


    try {
        const port = await navigator.serial.requestPort();
        await port.open({ baudRate: 9600 });

        // eslint-disable-next-line no-undef
        const decoder = new TextDecoderStream();

        port.readable.pipeTo(decoder.writable);

        const inputStream = decoder.readable;
        const reader = inputStream.getReader();


        while (counter < 10) {
            const { value, done } = await reader.read();
            uId += value
            console.log("Ez az uid: " + uId)

            if (value) {
                console.log("Ez a value: " + value);
                counter++
                console.log("A számláló: " + counter)

            }
            if (done) {
                console.log('[readLoop] DONE', done);
                reader.releaseLock();
                break;
            }
        }
        for (let i = 0; i < uId.length; i++) {
            console.log("+" + uId[i]);
        }
        uniqueCourierId = uId;
        return uId;


    } catch (error) {
        console.log("A serial nem elérhető!");
    }
}

//Soros port olvasás
//Ha a böngésző már kapcsolódott az arduino-hoz, akkor a böngésző a soros olvasásra már készen áll
//Ha a futár odaérinti az rfid taget, akkor az olvasás megtörténik, és az adatok a soros bufferben tárolódnak
//Amikor ezt a függvényt meghívom (amikor a futár megnyomja a bejelentkezés gombot), akkor kiolvasom a soros buffer tartalmát
const serialRead = async () => {

    try {

        // eslint-disable-next-line no-undef
        const decoder = new TextDecoderStream();

        //Ha olvastam a soros portot és utána újra olvasni akarom, akkor frissítenem kell az oldalt
        //Ilyen eset, amikor a futár rfid azonosítója nem megfelelő. Ekkor újra beolvassa
        //az rfid taget, de akkor az oldal frissíteni fog egyet
        const readableStream = selectedPort.readable;
        if (!readableStream.locked) {
            readableStream.pipeTo(decoder.writable);
        } else {
            window.location.reload();
        }
        
        //selectedPort.readable.pipeTo(decoder.writable);

        const inputStream = decoder.readable;
        const reader = inputStream.getReader();


        const { value, done } = await reader.read();


        if (value) {

        }
        if (done) {
            console.log('[readLoop] DONE', done);
            reader.releaseLock();

        }
        //Globális változónak értékadás
        uniqueCourierId = value;


    } catch (error) {
        console.log("Hiba a soros olvasás közben: " + error);
    }
}


//Egyedi futár azonosító lekérése. Globális változó
const getUniqueCourierId = () => {
    return uniqueCourierId;
}


//Adat küldése soros porton az arudinonak
//Csomag átvétele, automata kiürítése és automata feltöltése funkciók esetén
//Csomag átvételekor a bejöbő string 1 darab karakterből áll, tehát egy darab rekeszt nyitok ki
//Automata kiürítése és automata feltöltése funkciók esetén egyszerre több rekeszt nyitok ki
const serialWrite = async (boxNumbers) => {

    try {

        const textEncoder = new TextEncoderStream();
        const writableStreamClosed = textEncoder.readable.pipeTo(selectedPort.writable);

        const writer = textEncoder.writable.getWriter();

        //Egyesével küldöm a bájtokat
        /*
        for (let i = 0; i < boxNumbers.length; i++) {
            await writer.write(boxNumbers[i]);
        }*/

        //Ha egybe küldöm a stringet, akkor egyesével, azaz bájtonként fogja eküldeni
        //Mivel a soros port egyszerre egy byte-ot tud küldeni
        //Minden byte egy karakter
        //Ha például kinyílik az 1-es, 2-es és 4-es rekesz, akkor azt a string-et küldöm az arduino-nak, hogy: "124"
        //Az arudino pedig figyeli a bejövő karaktereket, és azokat a rekeszeket nyitja ki
        await writer.write(boxNumbers);

        await writer.close();
    } catch (error) {
        console.log("Hiba a soros adatküldés közben: " + error);
    }
}

