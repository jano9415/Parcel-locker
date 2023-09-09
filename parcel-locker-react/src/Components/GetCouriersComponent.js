import { Box, Button } from "@mui/material"
import { useEffect, useState } from "react";
import CourierService from "../Service/CourierService";
import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';



const GetCouriersComponent = () => {

    const [couriers, setCouriers] = useState([]);

    useEffect(() => {

        //Összes futár lekérése
        CourierService.getCouriers().then((response) => {

            setCouriers(response.data);
        },
            (error) => {

            })


    }, [])

    return (
        <Box>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Id</TableCell>
                            <TableCell align="right">Futár azonosító</TableCell>
                            <TableCell align="right">Veztéknév</TableCell>
                            <TableCell align="right">Keresztnév</TableCell>
                            <TableCell align="right">Körzet</TableCell>
                            <TableCell align="right"></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {couriers.map((courier) => (
                            <TableRow
                                key={courier.id}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell component="th" scope="row">
                                    {courier.id}
                                </TableCell>
                                <TableCell align="right">{courier.uniqueCourierId}</TableCell>
                                <TableCell align="right">{courier.lastName}</TableCell>
                                <TableCell align="right">{courier.firstName}</TableCell>
                                <TableCell align="right">{courier.storeCounty} megye</TableCell>
                                <TableCell align="right"><Button>Módosítás</Button></TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

        </Box>
    );
}

export default GetCouriersComponent;