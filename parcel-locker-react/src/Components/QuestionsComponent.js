import { Box } from "@mui/material"
import * as React from 'react';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

const QuestionsComponent = () => {

    return (
        <Box>

            <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                <Box>
                    <Typography sx={{ fontSize: 40 }}>Gyakran ismételt kérdések</Typography>
                    <Typography sx={{ fontSize: 20 }}>Email cím: swiftpost@gmail.com</Typography>
                    <Typography sx={{ fontSize: 20 }}>Telefonszám: 0630-376-12-88</Typography>
                    <Typography sx={{ fontSize: 30 }}>Gyakori csomagfeladási kérdések</Typography>
                </Box>
            </Box>

            <Box>
                <Accordion>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel1a-content"
                        id="panel1a-header"
                    >
                        <Typography>Hogyan tudok csomagot küldeni?</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Typography>
                            Csomagok a csomagautomatáknál és a webes vagy mobilos alkalmazásból tudsz.
                        </Typography>
                    </AccordionDetails>
                </Accordion>
                <Accordion>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel2a-content"
                        id="panel2a-header"
                    >
                        <Typography>Mennyibe kerül a csomagszállítás?</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Typography>
                            Kicsi csomag: 1950 Ft. Közepes csomag: 2400 Ft. Nagy csomag: 2900 Ft
                        </Typography>
                    </AccordionDetails>
                </Accordion>
                <Accordion>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel2a-content"
                        id="panel2a-header"
                    >
                        <Typography>Mennyi a kézbesítési idő?</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Typography>
                            A kézbesítési idő átlagosan 2-3 munkanap.
                        </Typography>
                    </AccordionDetails>
                </Accordion>
                <Accordion>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel2a-content"
                        id="panel2a-header"
                    >
                        <Typography>Hol vannak csomagautomaták?</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Typography>
                            Az automaták menüpontban meg tudod nézni az automatákat.
                            Ezt az opciót a mobilos alkalmazásban is megtalálod.
                        </Typography>
                    </AccordionDetails>
                </Accordion>
                <Accordion>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel2a-content"
                        id="panel2a-header"
                    >
                        <Typography>Honnan tudom, hogy van-e szabad hely az automatában?</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Typography>
                            Kattints az automaták menüpontra, majd a kiválasztott automatának nézd meg
                            a részleteit. Ott láthatod, hogy éppen mennyi szabad rekesz van.
                            Ezt az opciót a mobilos alkalmazásban is megtalálod.
                        </Typography>
                    </AccordionDetails>
                </Accordion>
                <Accordion>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel2a-content"
                        id="panel2a-header"
                    >
                        <Typography>Mekkora a maximális csomagméret?</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Typography>
                            A csomag nem haladhatja meg az alábbi méreteket:
                            Hosszúság: 198 cm.
                            Szélesség: 168 cm.
                            Magasság: 148 cm.
                        </Typography>
                    </AccordionDetails>
                </Accordion>
                <Accordion>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel2a-content"
                        id="panel2a-header"
                    >
                        <Typography>Mekkora a maximális súly, amit fel tudok adni?</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Typography>
                            A csomag súlya maximum 10 kg lehet.
                        </Typography>
                    </AccordionDetails>
                </Accordion>
                <Accordion>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel2a-content"
                        id="panel2a-header"
                    >
                        <Typography>Mennyi időm van elhelyezni a csomagot, amit az alkalmazásból adtam fel?</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Typography>
                            3 napod van elhelyezni a csomagot. Ezután a rendszer automatikusan törli azt.
                            Ha közben meggondolnád magad, a törlés időpontjáig te magad is törölheted
                            a felhasználói fiókodban.
                        </Typography>
                    </AccordionDetails>
                </Accordion>
            </Box>

            <Box sx={{ textAlign: 'center' }} className="d-flex justify-content-center">
                <Box>
                    <Typography sx={{ fontSize: 30 }}>Gyakori csomagátvételi kérdések</Typography>
                </Box>
            </Box>

            <Accordion>
                <AccordionSummary
                    expandIcon={<ExpandMoreIcon />}
                    aria-controls="panel2a-content"
                    id="panel2a-header"
                >
                    <Typography>Honnan tudom, hogy merre jár a csomagom, amit várok?</Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <Typography>
                        Kattints a csomagkövetés menüpontra. Írd be az email üzenetben kapott
                        csomagazonosítót majd a rendszer megmutatja, éppen melyik stádiumban van
                        a szállítási folyamat. Ezt az opciót a mobilos alkalmazásban is megtalálod.
                    </Typography>
                </AccordionDetails>
            </Accordion>
            <Accordion>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel2a-content"
                        id="panel2a-header"
                    >
                        <Typography>Mennyi időm van átvenni a csomagom?</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Typography>
                            3 napod van átvenni a csomagod. Ezután futárunk visszaszállítja azt a 
                            központi raktárba.
                        </Typography>
                    </AccordionDetails>
                </Accordion>
                <Accordion>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel2a-content"
                        id="panel2a-header"
                    >
                        <Typography>Mi történik, miután a csomagom visszakerül a központi raktárba?</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Typography>
                            Telefonon kérheted, hogya a csomagot szállítsák vissza az automatába. 
                            Ez plusz költséggel jár.
                        </Typography>
                    </AccordionDetails>
                </Accordion>
                <Accordion>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel2a-content"
                        id="panel2a-header"
                    >
                        <Typography>Hogyan tudok fizetni az automatáknál?</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <Typography>
                            Az automatáknál kizárólag bakkártyával tudsz fizetni.
                        </Typography>
                    </AccordionDetails>
                </Accordion>

        </Box>
    );
}

export default QuestionsComponent;