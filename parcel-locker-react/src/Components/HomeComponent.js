import React, { useState } from 'react';
import parcellockerimage from '../parcellockerimage.jpg'
import parcellockerimage2 from '../parcellockerimage2.jpg'
import parcellockerimage3 from '../parcellockerimage3.jpg'
import { Slide } from 'react-slideshow-image';
import 'react-slideshow-image/dist/styles.css'
import { Link } from 'react-router-dom';
import { Box, Typography } from '@mui/material';
import appstore from '../appstore.png';
import googleplay from '../googleplay.png';
import image1 from '../image1.png';
import image2 from '../image2.png';
import image11 from '../image11.png';
import image12 from '../image12.png';
import image13 from '../image13.png';
import image14 from '../image14.png';
import image15 from '../image15.png';
import image16 from '../image16.png';



const HomeComponent = () => {

  const spanStyle = {
    padding: '20px',
    background: '#efefef',
    color: '#000000'
  }

  const divStyle = {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundSize: 'cover',
    height: '560px'
  }
  const slideImages = [
    {
      url: parcellockerimage,
      caption: 'Csomagfeladás'
    },
    {
      url: parcellockerimage2,
      caption: 'Csomagátvétel'
    },
    {
      url: parcellockerimage3,
      caption: 'Küldemény nyomon követése',
    },
  ];






  return (
    <div>

      <div className="slide-container">
        <Slide>
          {slideImages.map((slideImage, index) => (
            <div key={index}>
              <div style={{ ...divStyle, 'backgroundImage': `url(${slideImage.url})` }}>
                <span style={spanStyle}>{slideImage.caption}</span>
              </div>
            </div>
          ))}
        </Slide>
      </div>

      <div className="d-flex justify-content-center">
        <div>
          <Typography sx={{ fontSize: 40 }}>Töltsd le a Swiftpost applikációt</Typography>
        </div>
      </div>

      <Box className="d-flex justify-content-center">
        <Box className="mt-3">
          <img src={appstore} alt="Logo" height="60" width="145" />
        </Box>
        <Box>
          <img src={googleplay} alt="Logo" height="89" width="170" />
        </Box>
      </Box>

      <Box className="d-flex justify-content-center">
        <Box>
          <img src={image1} alt="Logo" height="470" />
        </Box>
      </Box>

      <Box className="d-flex justify-content-center">
        <Box>
          <img src={image2} alt="Logo" height="470" />
        </Box>
      </Box>

      <Box className="d-flex justify-content-center">
        <Box>
          <Typography sx={{ fontSize: 40 }}>Csomagfeladás webes vagy mobilos alkalmazásból</Typography>
        </Box>
      </Box>

      <Box className="d-flex justify-content-center">
        <Box>
          <img src={image11} alt="Logo" height="470" />
        </Box>
      </Box>

      <Box className="d-flex justify-content-center">
        <Box>
          <img src={image12} alt="Logo" height="470" />
        </Box>
      </Box>

      <Box className="d-flex justify-content-center">
        <Box>
          <Typography sx={{ fontSize: 40 }}>Csomagfeladás az automatánál</Typography>
        </Box>
      </Box>


      <Box className="d-flex justify-content-center">
        <Box>
          <img src={image13} alt="Logo" height="470" />
        </Box>
      </Box>

      <Box className="d-flex justify-content-center">
        <Box>
          <img src={image14} alt="Logo" height="470" />
        </Box>
      </Box>

      <Box className="d-flex justify-content-center">
        <Box>
          <Typography sx={{ fontSize: 40 }}>Csomagátvétel</Typography>
        </Box>
      </Box>


      <Box className="d-flex justify-content-center">
        <Box>
          <img src={image15} alt="Logo" height="470" />
        </Box>
      </Box>

      <Box className="d-flex justify-content-center">
        <Box>
          <img src={image16} alt="Logo" height="470" />
        </Box>
      </Box>

    </div>
  );
}

export default HomeComponent;