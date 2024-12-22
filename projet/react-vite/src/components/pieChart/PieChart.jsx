import React from 'react';
import { Pie } from '@ant-design/plots';


const PieChart = ({ data }) => {
  const transformedData = data
  ? [
      { type: 'Total Loyer', value: data[0]?.total_loyer || 0 },
      { type: 'Bénéfice', value: data[0]?.benefice || 0 },
    ]
  : [];
  const config = {
    data:transformedData,
    angleField: 'value',
    colorField: 'type',
    label: {
      text: 'value',
      style: {
        fontWeight: 'bold',
      },
    },
    legend: {
      color: {
        title: false,
        position: 'right',
        rowPadding: 5,
      },
    },
  };
  
  return <Pie {...config} /> ;

  
  
};

export default PieChart;
