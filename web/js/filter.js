var filter_visible_state = false;
var FILTER_BLOCK_ID = 'filter_holder';

function getFilter(){
   var filter = new Object();
   var spItem = document.getElementById('filter_speciality');
   filter.spec_id = spItem.options[spItem.selectedIndex].value;
   var semItem = document.getElementById('filter_semester');
   filter.sem_id = semItem.options[semItem.selectedIndex].value;
   var chItem = document.getElementById('filter_pchair');
   filter.prchair_id = chItem.options[chItem.selectedIndex].value;
   var discItem = document.getElementById('filter_disc');
   filter.disc_id = discItem.options[discItem.selectedIndex].value;
   var zachetItem = document.getElementById('filter_zachet');
   filter.zachet = zachetItem.checked;
   var examItem = document.getElementById('filter_exam');
   filter.exam = examItem.checked;
   var div_zachetItem = document.getElementById('filter_div_zachet');
   filter.div_zachet = div_zachetItem.checked;
   var cpItem = document.getElementById('filter_cp');
   filter.cp = cpItem.checked;
   var cwItem = document.getElementById('filter_cw');
   filter.cw = cwItem.checked;
   return filter;
}
function resetFilter(){
   var spItem = document.getElementById('filter_speciality');
   spItem.selectedIndex = 0;
   var semItem = document.getElementById('filter_semester');
   semItem.selectedIndex = 0;
   var chItem = document.getElementById('filter_pchair');
   chItem.selectedIndex = 0;
   var discItem = document.getElementById('filter_disc');
   discItem.selectedIndex = 0;
   var zachetItem = document.getElementById('filter_zachet');
   zachetItem.checked = false;
   var examItem = document.getElementById('filter_exam');
   examItem.checked = false;
   var div_zachetItem = document.getElementById('filter_div_zachet');
   div_zachetItem.checked = false;
   var cpItem = document.getElementById('filter_cp');
   cpItem.checked = false;
   var cwItem = document.getElementById('filter_cw');
   cwItem.checked = false;
}
function applyFilter(){
   var filter = getFilter();
   $.ajax({
       url: _CTX + 'ajax/getFilteredPlans',
       type: 'POST',
       async: false,
       data: filter
   }).done(function(data){
       var item = document.getElementById('plans_holder');
       item.innerHTML = data;
   }).fail(function(){
       alert('ajax error!');
   });
}

function showFilter(){
    var filter_item = document.getElementById(FILTER_BLOCK_ID);
    if (filter_item === null || filter_item === undefined){
        return;
    }
    if (filter_visible_state){
        filter_item.setAttribute('style', 'display:none;');
    } else {
        filter_item.setAttribute('style', '');
    }
    filter_visible_state = !filter_visible_state;
}

