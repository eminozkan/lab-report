import React, { useState } from "react";
import './ViewReport.css'
import { Link } from 'react-router-dom'
import ImageComponent from '../ImageComponent/ImageComponent';

const ViewReportModal = ({ selectedReport, onClose }) => {
    const [isImageModalOpen, setisImageModalOpen] = useState(false);
    const [blob, setBlob] = useState(new Blob([]));

    const [fileNumber] = useState(selectedReport.fileNumber);
    const [patientId] = useState(selectedReport.patientIdNumber);
    const [fullName] = useState(selectedReport.fullName);
    const [diagnosisHeader] = useState(selectedReport.diagnosisHeader);
    const [diagnosisContent] = useState(selectedReport.diagnosisContent);
    const [reportDate] = useState(selectedReport.reportDate);
    const [reportImage] = useState(selectedReport.reportImage);
  


    const imageURL = blob ? URL.createObjectURL(blob) : null;

    // base64 ile şifreli şekilde tutulan veriyi Blob dizisine dönüştürür. ve ImageModalOpen değerini true yapar.
    const base64ToImage = (buffer) => {
        const blobData = atob(buffer);
        const arrayBuffer = new ArrayBuffer(blobData.length);
        const view = new Uint8Array(arrayBuffer);
        for (let i = 0; i < blobData.length; i++) {
            view[i] = blobData.charCodeAt(i);
        }
        setBlob(new Blob([arrayBuffer], { type: 'image/png' }));
        setisImageModalOpen(true)
    }

    return (
        <div className={'closeViewReportModal'}>
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2 className="header">Report Details</h2>
                <div className="detail"><div className="prefix">File Number :</div>{fileNumber}</div>
                <div className="detail"><div className="prefix">Full Name :</div>{fullName}</div>
                <div className="detail"><div className="prefix">Patient Id :</div>{patientId}</div>
                <div className="detail"><div className="prefix">Diagnosis Header :</div>{diagnosisHeader}</div>
                <div className="detail"><div className="prefix">Diagnosis Content :</div>{diagnosisContent}</div>
                <div className="detail"><div className="prefix">Report Date :</div>{reportDate}</div>
                <div className="detail"><div className="prefix">Report Image :</div><Link onClick={() => base64ToImage(reportImage)}>View Image</Link></div>
            </div>
            {isImageModalOpen && (
                <ImageComponent base64Image={imageURL} />
            )}
        </div>
    );
};

export default ViewReportModal;
