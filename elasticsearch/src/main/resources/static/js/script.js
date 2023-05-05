/*
const instance = new mdb.Datatable(document.getElementById('datatable'), users)

document.getElementById('datatable-search-input').addEventListener('input', (e) => {
  instance.input-group(e.target.value);
});*/

$(document).ready(function() {
    $('#users').DataTable( {
        responsive: true,
        "pageLength": 10
    } );
} );