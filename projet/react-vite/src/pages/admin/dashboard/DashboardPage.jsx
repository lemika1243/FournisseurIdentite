import React, { useState } from 'react';
import Statistique from '../../../components/statistique/Stat';
import ChartBarWithAnnotations from '../../../components/chartBar/ChartBarWithAnnotations';
import Bread from '../../../components/breadcrumb/Bread';
import TableResultWithoutAction from '../../../components/tables/TableResultWithoutAction';
import DataTableWithAction from '../../../components/tables/DataTableWithAction';
import { Col, Row } from 'antd';
import PieChart from '../../../components/pieChart/PieChart';
import FormFilterChart from '../../../components/filtres/FormFilterChart';
import FormFilterYearAndMonth from '../../../components/filtres/FormFilterYearAndMonth';
import { Card } from 'antd';
const DashboardPage = () =>{
    const [filterDataPieChart,setFilterDataPieChart] = useState(null);
    const [filterDataBarChart,setFilterDataBarChart] = useState(null);
    const [pieChartData, setPieChartData] = useState(null);
    const [barChartData, setBarChartData] = useState(null);


    const handleFilterPieSubmit = (data) => {
        console.log('Données filtrées:', data);
        setFilterDataPieChart(data);
        const result = [
            {total_loyer : 2500000,benefice:30000}
        ]
        setPieChartData(result);
  };
  const handleFilterBarSubmit = (data) => {
    console.log('Données filtrées:', data);
    setFilterDataBarChart(data);
    const result = [
        {mois:1,total_loyer : 2500000,commision:30000},
        {mois:2,total_loyer : 3500000,commision:30000},
        {mois:3,total_loyer : 7500000,commision:300000},
        {mois:4,total_loyer : 2500000,commision:30000},
        {mois:5,total_loyer : 1500000,commision:380000},
        {mois:6,total_loyer : 3500000,commision:70000},
        {mois:7,total_loyer : 2500000,commision:80000},
        {mois:8,total_loyer : 7500000,commision:30000},
        {mois:9,total_loyer : 2500000,commision:30000},
        {mois:10,total_loyer : 2500000,commision:30000},
        {mois:11,total_loyer : 2500000,commision:30000},
        {mois:12,total_loyer : 2500000,commision:300000},
    ]
    setBarChartData(result);
};
    const statitique = [
            { title: 'Active user', value: 112893 },
            { title: 'Users', value: 12500 },
            { title: 'Revenue', value: 389345 },
            { title: 'Orders', value: 985 },
    ]
    return (
    <div>
        <Bread user={'Administrateur'} page={'Tableau de Bord'} />
        <Statistique data={statitique}/>
        <Card bordered={true}>
        <Row>
            <Col span={10}>
            <FormFilterYearAndMonth onFilter={handleFilterBarSubmit}/>
            </Col>
            <Col span={10} offset={4}>
            <FormFilterChart onFilter={handleFilterPieSubmit} />
            </Col>
        </Row>
        
        <Row>
            <Col span={16}>
                <ChartBarWithAnnotations data={barChartData}  />
            </Col>
            <Col span={8}>
                <PieChart data={pieChartData}  />
            </Col>
        </Row>
        </Card>    
        <TableResultWithoutAction/>
    </div>
     
    );
}

export default DashboardPage;