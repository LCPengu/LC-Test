import {  useState, useEffect } from 'react';
import Data from  './Data.js'
const Fetch = ({fetch_url}) => {
  const [data, setData] = useState([]);
  
  useEffect(() => {
    fetch(fetch_url)
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        console.log(data);
        setData(data);

        if(data.objects != null){
            console.log("Long boi");
        }
      });
  }, [fetch_url]);
  
  if(data.objects != null) {
    return (
       <>
        <Data dataToDisplay={data.objects[0]}></Data>
        <Data dataToDisplay={data.objects[1]}></Data>
       </>
      );
  }
  else{
    return (
        <Data dataToDisplay={data}></Data>
      );
  }
};
export default Fetch;