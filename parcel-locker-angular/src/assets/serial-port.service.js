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



const serialRead = async () => {
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

const serialRead2 = async () => {

    let uId = ""
    let counter = 0;

    try {

        // eslint-disable-next-line no-undef
        const decoder = new TextDecoderStream();

        const readableStream = selectedPort.readable;
        if (!readableStream.locked) {
            readableStream.pipeTo(decoder.writable);
        }else{
            window.location.reload();
        }
        //selectedPort.readable.pipeTo(decoder.writable);

        const inputStream = decoder.readable;
        const reader = inputStream.getReader();



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
            
        }

        for (let i = 0; i < uId.length; i++) {
            console.log("+" + uId[i]);
        }
        uniqueCourierId = uId;
        return uId;


    } catch (error) {
        console.log("Hiba a soros olvasás közben: " + error);
    }
}


//Egyedi futár azonosító lekérése. Globális változó
const getUniqueCourierId = () => {
    return uniqueCourierId;
}

const serialWrite2 = async (pickingUpCode) => {

    try {

        const textEncoder = new TextEncoderStream();
        const writableStreamClosed = textEncoder.readable.pipeTo(selectedPort.writable);

        const writer = textEncoder.writable.getWriter();

        await writer.write(pickingUpCode);

        await writer.close();
    } catch (error) {
        console.log("Hiba a soros adatküldés közben: " + error);
    }
}

