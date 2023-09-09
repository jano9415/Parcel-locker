import { Box, Button, Chip, Divider, Modal, Slider, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import ParcelLockerService from '../Service/ParcelLockerService';

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


const ParcelLockerSaturationModalComponent = (props) => {

    const [open, setOpen] = useState(false);
    const [saturationData, setSaturationData] = useState({});


    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    useEffect(() => {

        //A kiválasztott automata telítettségi adatainak lekérése
        ParcelLockerService.getSaturationDatas(props.parcelLockerId).then(
            (response) => {
                setSaturationData(response.data);
            },
            (error) => {

            }
        )


    }, [])

    const showDatas = () => {
        setOpen(true);

    }



    return (
        <div>
            <Button onClick={showDatas}>Részletek</Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description">
                <Box sx={style}>
                    <Typography>Telítettségi adatok</Typography>
                    <Divider><Chip label="Kicsi" /></Divider>
                    <Typography>
                        Szabad kicsi rekeszek száma: {saturationData.amountOfSmallBoxes - saturationData.amountOfFullSmallBoxes}
                    </Typography>
                    <Divider><Chip label="Közepes" /></Divider>
                    <Typography>
                        Szabad közepes rekeszek száma: {saturationData.amountOfMediumBoxes - saturationData.amountOfFullMediumBoxes}
                    </Typography>
                    <Divider><Chip label="Nagy" /></Divider>
                    <Typography>
                        Szabad nagy rekeszek száma: {saturationData.amountOfLargeBoxes - saturationData.amountOfFullLargeBoxes}
                    </Typography>
                    <Divider><Chip label="Összes foglalt" /></Divider>
                    <Typography>
                        Összes foglalt rekeszek száma
                        <Slider
                            min={1}
                            max={30}
                            value={saturationData.amountOfFullSmallBoxes + saturationData.amountOfFullMediumBoxes +
                            saturationData.amountOfFullLargeBoxes}
                            valueLabelDisplay="auto"
                            onMouseDown={(e) => e.preventDefault()}
                            onTouchStart={(e) => e.preventDefault()}
                        />
                    </Typography>
                </Box>
            </Modal>

        </div>
    );
}

export default ParcelLockerSaturationModalComponent;
