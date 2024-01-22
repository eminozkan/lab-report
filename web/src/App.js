import './App.css';

import { Routes, Route} from 'react-router-dom'

import Home from './Components/Home/Home'
import LoginSignup from './Components/LoginSignup/LoginSignup'
import ReportInquiry from './Components/ReportInquiry/ReportInquiry';
import Reports from './Components/Reports/Reports';
import AddReport from './Components/AddReport/AddReport';
import Users from './Components/Users/Users'

function App() {
  return (
    <div className='container'>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/login' element={<LoginSignup />}/>
        <Route path='/report-inquiry' element={<ReportInquiry />}/>
        <Route path='/reports' element={<Reports />} />
        <Route path='/add-report' element={<AddReport />} />
        <Route path='/users' element={<Users />} />
      </Routes>
    </div>
  );
}

export default App;
