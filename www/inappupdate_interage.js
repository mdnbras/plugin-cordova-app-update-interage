var exec = require('cordova/exec');


exports.isUpdateAvailable = function (success, error) {
	exec(success, error, 'inappupdate_interage', 'isUpdateAvailable');
};


exports.update = function (arg0, success, error) {
	exec(success, error, 'inappupdate_interage', 'update', [arg0]);
};