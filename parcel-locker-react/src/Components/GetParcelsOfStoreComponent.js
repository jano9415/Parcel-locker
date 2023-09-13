import { Box, InputLabel, MenuItem, Select } from "@mui/material";
import { useEffect, useState } from "react";
import StoreService from "../Service/StoreService";

const GetParcelsOfStoreComponent = () => {

    const [parcels, setParcels] = useState([]);
    const [stores, setStores] = useState([]);
    const [selectedStoreId, setSelectedStoreId] = useState();


    useEffect(() => {

        //Központi raktárak lekérése
        StoreService.getStores().then((response) => {
            setStores(response.data);
        },
            (error) => {

            })



    }, [])

    const selectStore = (event) => {


        const selectedValue = event.target.value;
        setSelectedStoreId(selectedValue);

        console.log(selectedValue);

    }

    return (
        <Box>

            <Box className="d-flex justify-content-center">
                <Box>
                    <InputLabel>Csomagtípus kiválasztása</InputLabel>
                    <Select
                        id='storeid'
                        name='storeid'
                        onChange={selectStore}
                        value={selectedStoreId}
                        fullWidth
                    >

                        {
                            stores.map((store) => (
                                <MenuItem key={store.id} value={store.id}>{store.county}</MenuItem>
                            ))
                        }

                    </Select>
                </Box>
            </Box>

        </Box>
    );
}

export default GetParcelsOfStoreComponent;