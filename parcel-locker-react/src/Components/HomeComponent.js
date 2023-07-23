import React, { useState } from 'react';
import parcellockerimage from '../parcellockerimage.jpg'
import parcellockerimage2 from '../parcellockerimage2.jpg'
import parcellockerimage3 from '../parcellockerimage3.jpg'
import { Slide } from 'react-slideshow-image';
import 'react-slideshow-image/dist/styles.css'
import { Link } from 'react-router-dom';



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
          Ide jön mindenféle ismertető a csomagküldő szolgáltatásról.
          <p>Szöveg</p>
          <p>Szöveg</p>
          <p>Szöveg</p>
          <p>Szöveg</p>
          <p>Szöveg</p>
          <p>Szöveg</p>
          <p>Szöveg</p>
          <p>Szöveg</p>
          <p>Szöveg</p>
          <p>Szöveg</p>
        </div>
      </div>

    </div>
  );
}

export default HomeComponent;