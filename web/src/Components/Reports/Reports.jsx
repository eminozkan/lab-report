import React, { useState } from 'react'
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import Navbar from '../Navbar/Navbar'
import { Link } from 'react-router-dom'

import './Reports.css'
import EditReport from '../EditReport/EditReport';
import ViewReportModal from '../ViewReport/ViewReport';

const Reports = () => {
    const [checkUserAuth, setCheckUserAuth] = useState('true')
    const [dataList, setDataList] = useState([]);


    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [isViewReportModalOpen, setIsViewReportModalOpen] = useState(false)
    const [isDecreasing, setIsDecreasing] = useState(false);
    const [selectedIndex, setSelectedIndex] = useState('');

    // Bir raporun sağ tarafında bulunan kalem sembolüne tıklandığında IsEditModalOpen değerini true yapar ve SelectedIndex değişkenine parametre olarak alınan değeri atar.
    const openEditModal = (index) => {
        setIsEditModalOpen(true)
        setSelectedIndex(index)
    }
    const closeEditModal = () => setIsEditModalOpen(false);


    // Tarihe göre sıralama değiştiğinde Apiden raporları getiren fonksiyondur.
    const handleGetReportsByDate = async () => {
        let path = isDecreasing ? "/desc" : "/asc"
        try {
            const response = await axios.get("/api/v1/reports" + path);
            setDataList(response.data);
            setIsDecreasing(!isDecreasing)
        } catch (error) {
            if (error.response) {
                toast.error(error.response.data.message);
            } else if (error.request) {
                console.log(error.request);
            }
        }
    }

    const openViewModal = (index) => {
        setIsViewReportModalOpen(true)
        setSelectedIndex(index)
    }

    const closeViewModal = () => {
        setIsViewReportModalOpen(false)
    }

    if (checkUserAuth) {
        handleGetReportsByDate()
        setCheckUserAuth(false);
    }


    // search-bar isimli text input değeri değiştiğinde Api ye istekte bulunup, girilen değere göre raporların getirilmesini sağlar
    const handleSearch = async (e) => {
        let path = isDecreasing ? "/desc" : "/asc"
        path += "?search=" + e
        try {
            const response = await axios.get("/api/v1/reports" + path);
            setDataList(response.data);
            setIsDecreasing(!isDecreasing)
        } catch (error) {
            if (error.response) {
                toast.error(error.response.data.message);
            } else if (error.request) {
                console.log(error.request);
            }
        }
    }

    // Raporun sağ tarafında bulunan X sembolüne tıklandığında Raporu silmek için Api ye istekte bulunur.
    const deleteReport = async (index) => {
        var id = dataList[index].reportId
        try {
            await axios.delete("/api/v1/reports/" + id);
            handleGetReportsByDate()
            toast.success("Report has been deleted.")
        } catch (error) {
            toast.error("You don't have rights to delete the report");
        }
    }

    const arrowKey = isDecreasing ? "↓" : "↑"

    return (
        <>
            <Navbar pageName="Reports" />
            <div className="card" style={{ width: 1500 }}>
                <input
                    type="text"
                    placeholder="Search..."
                    className='input'
                    id="search-bar"
                    onChange={(e) => handleSearch(e.target.value)}
                />
                <div className="reports">
                    <div className="info">
                        <div className="value reportHeader">File Number</div>
                        <div className="value reportHeader">Patient Name</div>
                        <div className="value reportHeader">Patient ID</div>
                        <div className="value reportHeader">Report Header</div>
                        <div className="value reportHeader" onClick={() => handleGetReportsByDate()}><div id='date' style={{ cursor: 'pointer' }}>Report Date {arrowKey}</div></div>
                        <div className="value reportHeader details">Details</div>
                        <div className="value reportHeader details">Operations</div>
                    </div>
                    {dataList.map((item, index) => (
                        <div className="report" key={item.id}>
                            <div className="value">{item.fileNumber}</div>
                            <div className="value">{item.fullName}</div>
                            <div className="value">{item.patientIdNumber}</div>
                            <div className="value">{item.diagnosisHeader}</div>
                            <div className="value">{item.reportDate}</div>
                            <Link className="value" onClick={() => openViewModal(index)}>View Details</Link>
                            <div className="value">
                                <Link onClick={() => openEditModal(index)} style={{ marginRight: 10 }}>🖊️</Link>
                                <Link onClick={() => deleteReport(index)} style={{ color: "red" }}>X</Link>
                            </div>

                        </div>
                    ))}
                </div>
            </div>
            {isEditModalOpen && (
                <EditReport reportId={dataList[selectedIndex]} onClose={closeEditModal} updateReports={handleGetReportsByDate}/>
            )}

            {isViewReportModalOpen && (
                <ViewReportModal selectedReport={dataList[selectedIndex]} onClose={closeViewModal} />
            )}
            <ToastContainer />
        </>

    )

}

export default Reports;