function saveRoomToBuilding(){
    var b_select = document.getElementById('building_select_id');
    var selected_building = b_select.options[b_select.selectedIndex].value;
    if (selected_building === null || selected_building === undefined){
        alert('не обраний корпус');
        return;
    }
    var input = document.getElementById('room_input_id');
    if (input.value.length === 0){
        return;
    }
    $.ajax({
        url: 'ajax/saveRoomToBuilding',
        type: 'POST',
        async: false,
        data: {b_id:selected_building, room_name: input.value}
    }).done(function(response){
        if(!response.success){
            alert(response.message);
            return;
        }
        onSelectLoadRooms();
    }).fail(function(){
        alert('(ajax) ошибка');
    });
}

function saveNewBuilding(){
    var input = document.getElementById('building_input_id');
    if (input.value.length === 0){
        return;
    }
    $.ajax({
        url: 'ajax/saveNewBuilding',
        type: 'POST',
        async: false,
        data: {b_name : input.value}
    }).done(function(response){
        if(!response.success){
            alert(response.message);
            return;
        }
        window.location.reload();
    }).fail(function(){
        alert('(ajax) ошибка');
    });
}

function onSelectLoadRooms(){
    var b_select = document.getElementById('building_select_id');
    var selected_building = b_select.options[b_select.selectedIndex].value;
    if (selected_building === null || selected_building === undefined){
        return;
    }
    var rooms_block = document.getElementById('rooms_block_id');
    if (rooms_block === null || rooms_block === undefined){
        return;
    }
    $.ajax({
        url: 'ajax/loadRoomsByBuilding',
        type: 'POST',
        async: false,
        data: { id : selected_building }
    }).done(function(response){
        rooms_block.innerHTML = response;
    }).fail(function(){
        alert('(ajax) ошибка');
    });
}
