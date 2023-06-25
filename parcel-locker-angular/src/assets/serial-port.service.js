let uniqueCourierId;


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
        uniqueCourierId = uId;
        return uId;
        

    } catch (error) {
        console.log("A serial nem elérhető!");
    }
}

const getUniqueCourierId = () => {
    return uniqueCourierId;
}

