import React, { useState } from 'react';
import "./EditReport.css"
import axios from 'axios';
import { toast } from 'react-toastify';

const EditReport = ({ reportId, onClose, updateReports }) => {
  const [fileNumber, setFileNumber] = useState(reportId.fileNumber);
  const [patientId, setPatientId] = useState(reportId.patientIdNumber);
  const [fullName, setfullName] = useState(reportId.fullName);
  const [diagnosisHeader, setDiagnosisHeader] = useState(reportId.diagnosisHeader);
  const [diagnosisContent, setDiagnosisContent] = useState(reportId.diagnosisContent);
  const [reportDate, setReportDate] = useState(reportId.reportDate);
  const [reportImage, setReportImage] = useState(null);

  console.log(reportId)

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setReportImage(file);
  };

  // EditModal üzerinde Submit butonuna tıklandığında raporu güncellemek için Api'ye istekte bulunur.
  const handleSubmit = async (e) => {
    e.preventDefault();
    const updatedReport = {
      fileNumber,
      patientId,
      fullName,
      diagnosisHeader,
      diagnosisContent,
      reportDate,
      reportImage
    };

    try {
      const response = await axios.post('/api/v1/reports/' + reportId.reportId, updatedReport, {
          headers: {
              'Content-Type': 'multipart/form-data',
          },
      });

      toast.success("Report has been updated.")
      onClose()
      updateReports()
  } catch (error) {
      if (error.response) {
          toast.error(error.response.data.message);
      } else if (error.request) {
          console.log(error.request);
      }
  }
  };

  return (
    <div className="modal">
      <div className="modal-content">
        <div className="close" onClick={onClose}>X</div>
        <form onSubmit={handleSubmit}>
          <div className="header" style={{marginTop : -40}}><h2>Update Report</h2></div>
          <input
            type="text"
            className='input'
            placeholder='File Number'
            value={fileNumber}
            onChange={(e) => setFileNumber(e.target.value)}
          />
          <input
            type="text"
            className='input'
            placeholder='Patient Id'
            value={patientId}
            onChange={(e) => setPatientId(e.target.value)}
          />
          <input
            type="text"
            className='input'
            placeholder='Patient Name'
            value={fullName}
            onChange={(e) => setfullName(e.target.value)}
          />
          <input
            type="text"
            className='input'
            placeholder='Diagnosis Header'
            value={diagnosisHeader}
            onChange={(e) => setDiagnosisHeader(e.target.value)}
          />
          <input
            type="textbox"
            className='input'
            placeholder='Diagnosis Content'
            value={diagnosisContent}
            onChange={(e) => setDiagnosisContent(e.target.value)}
          />
          <input
            type="date"
            className='input'
            placeholder='Report Date'
            value={reportDate}
            onChange={(e) => setReportDate(e.target.value)}
          />
          <label className='label'>
            Report Image:
            <input type='file' className='input' onChange={handleFileChange} />
          </label>
          <button className='button' type="submit">Update</button>
        </form>
      </div>
    </div>
  );
};

export default EditReport;
