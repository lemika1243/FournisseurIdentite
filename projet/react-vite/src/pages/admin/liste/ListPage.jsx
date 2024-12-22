import React from 'react';
import DataTableWithAction from '../../../components/tables/DataTableWithAction';
import Bread from '../../../components/breadcrumb/Bread';
const ListPage = () =>{
    return(
        <div>
            <Bread user={'Administrateur / Salarié'} page={'Liste'} />
            <DataTableWithAction />
        </div>
        
    );
}
export default ListPage;