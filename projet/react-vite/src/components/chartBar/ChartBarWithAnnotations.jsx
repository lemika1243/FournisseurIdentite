import React from 'react';
import { Column } from '@ant-design/plots';
import { Card } from 'antd';
import { moisLabels } from '../../utils/constante'; // Importez moisLabels

const ChartBarWithAnnotations = ({ data }) => {

  // Transformation des données pour le graphique
  const transformedData = (data || []).flatMap((item) => [
    { 
      mois: moisLabels[item.mois - 1], 
      type: 'Total Loyer', 
      value: item.total_loyer, 
    },
    { 
      mois: moisLabels[item.mois - 1], 
      type: 'Commission', 
      value: item.commision,
    },
  ]);

  const config = {
    data: transformedData,
    xField: 'mois', 
    yField: 'value', 
    seriesField: 'type', 
    isGroup: true,
    colorField: 'type', 
    color: ({ type }) => type === 'Total Loyer' ? '#1979C9' : '#D62A0D',
  };

  return (
    <Card bordered={true} style={{ marginBottom: 24, marginTop: 24 }}>
      <Column {...config} />
    </Card>
  );
};

export default ChartBarWithAnnotations;