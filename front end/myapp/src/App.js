import './App.css';
import Fetch from './Fetch.js';

function App() {
  var url_google = "http://localhost/v1/google-status"
  var url_amazon = "http://localhost/v1/amazon-status"
  var url_all = "http://localhost/v1/all-status"
  return (
    <div className="App">
      <h3>Google Status</h3>
      <Fetch fetch_url = {url_google}></Fetch>
      <h3>Amazon Status</h3>
      <Fetch fetch_url = {url_amazon}></Fetch>
      <h3>All Status</h3>
      <Fetch fetch_url = {url_all}></Fetch>
    </div>
  );
}

export default App;