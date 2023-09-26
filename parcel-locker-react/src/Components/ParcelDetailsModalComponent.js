import { Box, Button, Chip, Divider, InputLabel, MenuItem, Modal, Paper, Select, Typography } from "@mui/material";
import MyProfileMenuComponent from "./MyProfileMenuComponent";
import { useEffect, useState } from "react";
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

const ParcelDetailsModalComponent = (props) => {

    const [open, setOpen] = useState(false);

    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);


    useEffect(() => {


    }, [])

    const showDatas = () => {
        setOpen(true);
    }

    return (
        <Box>
            <Button onClick={showDatas}>Részletek</Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description">
                <Box sx={style}>
                    <Typography>Hol tart a csomagom?</Typography>

                    <Box>
                        <Divider><Chip label="Címzett" /></Divider>
                        <Typography>Címzett neve: {props.parcel.receiverName}</Typography>
                        <Typography>Címzett email címe: {props.parcel.receiverEmailAddress}</Typography>
                    </Box>

                    {props.parcel.sendingExpirationDate != null && (
                        <Box>
                            <Divider><Chip label="Előzetesen feladva" /></Divider>
                            <Typography>A csomagot a webes vagy mobilos alkalmazásból már feladtad,
                                de az automatában még nem helyezted el.
                            </Typography>
                            <Typography>Itt tudod feladni: {
                                props.parcel.shippingFromPostCode + " " + props.parcel.shippingFromCity + " " +
                                props.parcel.shippingFromStreet
                            }</Typography>
                            <Typography>Eddig tudod feladni: {
                                props.parcel.sendingExpirationDate + " " + props.parcel.sendingExpirationTime
                            }
                            </Typography>
                            <Typography>Feladási kód: {props.parcel.sendingCode}</Typography>
                        </Box>
                    )}

                    {props.parcel.sendingDate != null && (
                        <Box className='mt-2'>
                            <Divider><Chip label="Csomag feladva" /></Divider>
                            <Typography>Csomag feladva {props.parcel.sendingDate + " " + props.parcel.sendingTime + "-kor"}</Typography>
                            <Typography>Itt adtad fel: {
                                props.parcel.shippingFromPostCode + " " + props.parcel.shippingFromCity + " " +
                                props.parcel.shippingFromStreet
                            }</Typography>
                            <Typography>Ide fog megérkezni {
                                props.parcel.shippingToPostCode + " " + props.parcel.shippingToCity + " " +
                                props.parcel.shippingToStreet
                            }</Typography>
                        </Box>
                    )}

                    {props.parcel.shippingDate != null && (
                        <Box>
                            <Divider><Chip label="Csomag megérkezett" /></Divider>
                            <Typography>A csomagot még nem vették át.</Typography>
                            <Typography>Csomag leszállítva {props.parcel.shippingDate + " " + props.parcel.shippingTime + "-kor"}</Typography>
                            <Typography>Csomag elhelyezve itt: {
                                props.parcel.shippingToPostCode + " " + props.parcel.shippingToCity + " " + props.parcel.shippingToStreet
                            }</Typography>
                        </Box>
                    )}

                    {props.parcel.pickingUpDate != null && (
                        <Box>
                            <Divider><Chip label="Csomagot átvették" /></Divider>
                            <Typography>Csomag átvéve {props.parcel.pickingUpDate + " " + props.parcel.pickingUpTime + "-kor"}</Typography>
                        </Box>
                    )}
                    <Button onClick={() => { setOpen(false) }}>Vissza</Button>
                </Box>
            </Modal>

        </Box>
    );
}

export default ParcelDetailsModalComponent;