import React, { useState } from 'react';
import './ReportInquiry.css';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import { Link } from 'react-router-dom'
import left_arrow from '../Assets/left_arrow.png'
import ImageComponent from '../ImageComponent/ImageComponent';

const ReportInquiry = () => {
    const [patientId, setPatientIdNumber] = useState('');
    const [dataList, setDataList] = useState([]);
    const [blob, setBlob] = useState(new Blob([]));
    const [isModalOpen, setIsModalOpen] = useState(false);
    const handleGetReports = async () => {
        setIsModalOpen(false)
        try {
            const response = await axios.post("/api/v1/patients", {
                patientId
            });
            setDataList(response.data);
        } catch (error) {
            if (error.response) {
                toast.error(error.response.data.message);
            } else if (error.request) {
                console.log("Hello");
            }
        }
    }
    const base64ToImage = (buffer) => {
        const blobData = atob(buffer);
        const arrayBuffer = new ArrayBuffer(blobData.length);
        const view = new Uint8Array(arrayBuffer);
        for (let i = 0; i < blobData.length; i++) {
            view[i] = blobData.charCodeAt(i);
        }
        setBlob(new Blob([arrayBuffer], { type: 'image/png' }));
        setIsModalOpen(true)
    }


    const closeModal = () => {
        setIsModalOpen(false);
        setBlob(null);
    }

    const imageURL = blob ? URL.createObjectURL(blob) : null;

    const handleTextChange = (e) =>{
        setPatientIdNumber(e.target.value)
        closeModal()
    }

    return (
        <div className="container">
            <div className="card">
                <Link to="/" className="home">
                    <img src={left_arrow} alt='left-arrow' className='arrow' />
                    <div className="back">
                        Back To Home Page
                    </div>
                </Link>
                <div className="header">
                    <div className="text">Report Inquiry</div>
                </div>
                <div className="inputs">
                    <div className="input">
                        <input type="text"
                            id='patientIdNumber'
                            value={patientId}
                            placeholder='Patient Id Number'
                            onChange={(e) => handleTextChange(e)} />
                    </div>
                    <div className="submit-container">
                        <div className="submit" onClick={handleGetReports}>
                            Inquire
                        </div>
                    </div>
                </div>
                <div className="reports">
                    {dataList.map((item, index) => (
                        <div className="content" key={item.id}>
                            <div className="content">{item.diagnosisHeader}</div>
                            <div className="content">{item.diagnosisContent}</div>
                            <div className="content">{item.reportDate}</div>
                            <Link className="content" onClick={() => base64ToImage(item.reportImage)}>View Image</Link>
                            <div>_________________________________</div>
                        </div>
                    ))}
                </div>
            </div>
            {isModalOpen && (
                <ImageComponent base64Image={imageURL} closeModal={closeModal}/>
            )}
            <ToastContainer />
        </div>
    );
}

export default ReportInquiry;
