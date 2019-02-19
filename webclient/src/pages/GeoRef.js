import React from 'react';
import { withRouter } from 'react-router';

const GeoRef = ({ match }) => {
  return (
    <main>
      <h1>GeoRef</h1>
      <p>SG {match.params.id}</p>
    </main>
  )
};

export default withRouter(GeoRef);