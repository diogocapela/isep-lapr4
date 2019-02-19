import React from 'react';
import { withRouter } from 'react-router';

const REST = ({ match }) => {
  return (
    <main>
      <h1>REST</h1>
      <p>SG {match.params.id}</p>
    </main>
  )
};

export default withRouter(REST);