var BACKGROUND_ID = 'lesson_window_background';
var USER_ROLES_WINDOW_ID = 'user_roles_window_id';

var _CTX = '';//'Automatization_netbeans_/';

var curent_window_level = 0;

function showUserRolesWindow(id) {
    if (id === null || id === undefined) {
        return;
    }
    $.ajax({
        url: 'users/showUserRolesWindow',
        type: 'POST',
        async: false,
        data: {id: id}
    }).done(function(response) {
        var block = document.createElement('div');
        block.setAttribute('id', USER_ROLES_WINDOW_ID);
        block.innerHTML = response;
        var backgr = createLessonBackground();
        document.body.appendChild(backgr);
        document.body.appendChild(block);
        curent_window_level++;
    }).fail(function() {
        alert('ошибка при отображении окна(ajax)');
    });
}

function loadUserRoles(id) {
    if (id === null || id === undefined) {
        return;
    }
    $.ajax({
        url: 'users/loadUserRoles',
        type: 'POST',
        async: false,
        data: {id: id}
    }).done(function(response) {
        var roles_block = document.getElementById('roles_list_id');
        roles_block.innerHTML = response;
    }).fail(function() {
        alert('(ajax) ошибка');
    });
}

function addRoleToUser(id) {
    if (id === null || id === undefined) {
        return;
    }
    var sel = document.getElementById('user_roles_roles_select');
    if (sel === null || sel === undefined) {
        alert('не найден select блок ролей');
    }
    if (sel.options.length === 0) {
        return;
    }
    var roleId = sel.options[sel.selectedIndex].value;
    if (roleId === null || roleId === undefined) {
        return;
    }
    $.ajax({
        url: 'users/saveRoleToUser',
        type: 'POST',
        async: false,
        data: {role_id: roleId, id: id}
    }).done(function(response) {
        if (!response.success) {
            alert(response.message);
            return;
        }
        loadUserRoles(id);
    }).fail(function() {
        alert('(ajax) ошибка');
    });
}

function removeRole(role_id, id) {
    if (id === null || id === undefined) {
        return;
    }
    if (role_id === null || role_id === undefined) {
        return;
    }
    $.ajax({
        url: 'users/removeUserRole',
        type: 'POST',
        async: false,
        data: {role_id: role_id, id: id}
    }).done(function(response) {
        if (!response.success) {
            alert(response.message);
            return;
        }
        loadUserRoles(id);
    }).fail(function() {
        alert('(ajax) ошибка');
    });
}

function deleteUser(id){
    if (id === null || id === undefined) {
        return;
    }
    $.ajax({
        url: 'users/removeUser',
        type: 'POST',
        async: false,
        data: {id: id}
    }).done(function(response) {
        if (!response.success) {
            alert(response.message);
            return;
        }
        window.location.reload();
    }).fail(function() {
        alert('(ajax) ошибка');
    });
}

function closeWindow(id) {
    var window = document.getElementById(id);
    if (window !== undefined && window !== null) {
        document.body.removeChild(window);
        if (curent_window_level === 1) {
            removeLessonBackground();
        }
        curent_window_level--;
    }
}

function createLessonBackground() {
    var background = document.createElement('div');
    background.setAttribute('id', BACKGROUND_ID);
    return background;
}

function removeLessonBackground() {
    var background = document.getElementById(BACKGROUND_ID);
    if (background === null || background === undefined) {
        return;
    }
    document.body.removeChild(background);
}