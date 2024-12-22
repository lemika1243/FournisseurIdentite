import React from 'react';
import Statistique from '../../../components/statistique/Stat';
import TableResultWithAction from '../../../components/tables/TableResultWithAction';
import Bread from '../../../components/breadcrumb/Bread';
const DashboardPage = () => {
  const dataStat = [
    { title: 'Active user', value: 112893 },
    { title: 'Users', value: 12500 },
    { title: 'Revenue', value: 389345 },
    { title: 'Orders', value: 985 },
  ];
return(
  <div>
    <Bread user={'Utilisateur'} page={'Tableau de Bord'} />
    <Statistique data={dataStat} />
    <TableResultWithAction />
  </div>
  
);
}
export default DashboardPage;


