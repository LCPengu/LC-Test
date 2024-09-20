const Data = ({dataToDisplay}) => {
  return (
    <div>
     <p>{dataToDisplay.URL}</p>
     <p>{dataToDisplay.Response_Code}</p>
     <p>{dataToDisplay.Duration}</p>
     <p>{dataToDisplay.Date}</p>
    </div>
  );
};
export default Data;