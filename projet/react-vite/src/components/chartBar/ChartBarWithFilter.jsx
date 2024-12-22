import React, { useState } from 'react';
import { Column } from '@ant-design/plots';
import { Select } from 'antd';

const { Option } = Select;

const ChartBarWithFilter = () => {
  const [filter, setFilter] = useState(['London']); // Initialisation avec le filtre "London"

  const data = [
    { month: 'January', averageRainfall: 18.9, city: 'London' },
    { month: 'January', averageRainfall: 28.8, city: 'New York' },
    { month: 'January', averageRainfall: 39.3, city: 'Tokyo' },
    { month: 'February', averageRainfall: 12.4, city: 'London' },
    { month: 'February', averageRainfall: 23.2, city: 'New York' },
    { month: 'February', averageRainfall: 34.5, city: 'Tokyo' },
    // Ajoute plus de données si nécessaire
  ];

  const config = {
    data,
    xField: 'month',
    yField: 'averageRainfall',
    colorField: 'city',
    isGroup: true,
    seriesField: 'city',
    style: { inset: 5 },
    slider: {
      start: 0.0,
      end: 1.0,
    },
    legend: {
      position: 'top',
    },
    interactions: [
      {
        type: 'legend-filter', // Permet de filtrer les données affichées par la légende
      },
    ],
  };

  const handleFilterChange = (value) => {
    setFilter(value);
  };

  const filteredData = data.filter((item) => filter.includes(item.city));

  return (
    <div>
      {/* Menu de filtre */}
      <div style={{ marginBottom: 16 }}>
        <Select
          mode="multiple"
          placeholder="Filtrer par ville"
          value={filter}
          onChange={handleFilterChange}
          style={{ width: '100%' }}
        >
          <Option value="London">London</Option>
          <Option value="New York">New York</Option>
          <Option value="Tokyo">Tokyo</Option>
        </Select>
      </div>

      {/* Graphique */}
      <Column {...config} data={filteredData} />
    </div>
  );
};

export default ChartBarWithFilter;
