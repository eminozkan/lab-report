import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'
import Navbar from '../Navbar/Navbar'
import "./AddReport.css"
import { ToastContainer, toast } from 'react-toastify';


const AddReport = () => {
    const navigate = useNavigate()
    const [formData, setFormData] = useState({
        fullName: '',
        fileNumber: '',
        patientIdNumber: '',
        diagnosisHeader: '',
        diagnosisContent: '',
        reportDate: '',
        reportImage: null,
    });

    // Sayfadaki inputlar içerisinin değeri değiştiğinde formData  içerisindeki gerekli değişikliği yapar.
    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    // Sayfa içerisindeki dosya yükleme kısmından yeni bir dosya yüklendiğinde formData içerisindeki reportImage değerini değiştirir.
    const handleImageChange = (e) => {
        setFormData({
            ...formData,
            reportImage: e.target.files[0],
        });
    };

      const formatReportDate = (date) => {
    return new Intl.DateTimeFormat('en-US', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
    }).format(date);
  };
  
    // Submit butonuna tıklandığında verileri yeni bir formData oluşturarak Apiye bu formData ile istekte bulunur.
    const handleSubmit = async (e) => {
        e.preventDefault();

        const formDataToSend = new FormData();
        formDataToSend.append('fullName', formData.fullName);
        formDataToSend.append('fileNumber', formData.fileNumber);
        formDataToSend.append('patientIdNumber', formData.patientIdNumber);
        formDataToSend.append('diagnosisHeader', formData.diagnosisHeader);
        formDataToSend.append('diagnosisContent', formData.diagnosisContent);
        let date = formData.reportDate.replace('.', "-")
        formDataToSend.append('reportDate', formData.reportDate.replace('.', "-"));
        formDataToSend.append('reportImage', formData.reportImage);

        try {
            const response = await axios.post('/api/v1/reports', formDataToSend, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });

            toast.success("Report has been saved.")
        } catch (error) {
            if (error.response) {
                toast.error(error.response.data.message);
            } else if (error.request) {
                console.log(error.request);
            }
        }
    };

    return (
        <div>
            <Navbar pageName="Add Report" />
            <div className="card">
                <h2 className='addreport'>Add Report</h2>
                <form onSubmit={handleSubmit}>
                    <div className="inputs">
                        <input type="text" className='input' name="fullName" value={formData.fullName} onChange={handleChange} placeholder='Patient Full Name' />
                        <input type="text" className='input' name="fileNumber" value={formData.fileNumber} onChange={handleChange} placeholder='File Number' />
                        <input type="text" className='input' name="patientIdNumber" value={formData.patientIdNumber} onChange={handleChange} placeholder='Patient Id Number' />
                        <input type="text" className='input' name="diagnosisHeader" value={formData.diagnosisHeader} onChange={handleChange} placeholder='Diagnosis Header' />
                        <input type="text" className='input' name="diagnosisContent" value={formData.diagnosisContent} onChange={handleChange} placeholder='Diagnosis Content' />
                        <input type="date" className='input' name="reportDate" value={formData.reportDate} onChange={handleChange} placeholder='Report Date' />
                        <label className='label'> Report Image : 
                            <input type="file" name="reportImage" onChange={handleImageChange} style={{marginLeft: 20}} />
                        </label>
                        <button type="submit">Submit</button>
                    </div>
                </form>
            </div>
            <ToastContainer />
        </div>
    );
};

export default AddReport;
