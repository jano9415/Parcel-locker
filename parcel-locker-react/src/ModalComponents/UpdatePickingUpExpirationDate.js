import { Box, Button, Modal, Typography } from "@mui/material";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { useEffect, useState } from "react";
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from "dayjs";
import ParcelService from "../Service/ParcelService";


const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 64,
    p: 4,
};

const UpdatePickingUpExpirationDate = (props) => {

    const [selectedDate, setSelectedDate] = useState(dayjs(props.parcel.pickingUpExpirationDate));

    const [open, setOpen] = useState(false);

    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);


    useEffect(() => {


    }, [])

    const showDatas = () => {
        setOpen(true);
    }

    //Új dátum kiolvasása
    const selectNewDate = (newDate) => {
        setSelectedDate(newDate);
    }

    //Új dátum küldése
    const sendNewDate = () => {

        const formattedDate = selectedDate.format('YYYY-MM-DD');
        
        ParcelService.updatePickingUpExpirationDate(props.parcel.id, formattedDate).then((response) => {

            console.log(response.data);

            if(response.data.message === "notFound"){

            }
            if(response.data.message === "expirationDateIsNull"){

            }
            if(response.data.message === "successFulUpdating"){
                window.location.reload();
            }
        },
            (error) => {

            })
    }


    return (
        <Box>
            <Button onClick={showDatas}>Kattints ide</Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description">
                <Box sx={style}>
                    <Typography>Itt tudod módosítani a csomagátvételi lejárati dátumot.</Typography>
                    <Typography>Válaszd ki a megfelelő dátumot majd kattints a küldés gombra.</Typography>
                    <Typography>Egy módosítással maximum három nappal tudod meghosszabítani a lejárati dátumot.</Typography>
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <DemoContainer components={['DatePicker']}>
                            <DatePicker label="Jelenlegi lejárati dátum"
                                defaultValue={dayjs(props.parcel.pickingUpExpirationDate)}
                                minDate={dayjs(props.parcel.pickingUpExpirationDate)}
                                maxDate={dayjs(props.parcel.pickingUpExpirationDate).add(3, 'day')}
                                onChange={selectNewDate}
                            />
                        </DemoContainer>
                    </LocalizationProvider>
                    <Button onClick={sendNewDate}>Küldés</Button>
                    <Button onClick={() => { setOpen(false) }}>Vissza</Button>
                </Box>
            </Modal>

        </Box>
    );
}

export default UpdatePickingUpExpirationDate;