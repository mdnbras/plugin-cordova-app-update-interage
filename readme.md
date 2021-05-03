## Install plugin command
```shell
ionic cordova plugin add plugin-cordova-app-update-interage
```

## Remove plugin command
```shell
ionic cordova plugin rm plugin-cordova-app-update-interage
```

## Check app update available or not
```js
cordova.plugins.inappupdate.isUpdateAvailable(success,fail);
```

- Example
```js
cordova.plugins.inappupdate.isUpdateAvailable(success,fail);

function success(result)
{
 	if(JSON.parse(result))
	{
		alert("App update available");
	}
	else
	{
		alert("No app update available");
	}
}
function fail(result)
{
 console.log(result);
}
```

## Start flexible Update
```js
cordova.plugins.inappupdate.update("flexible",function(){},function(){});
```

## Start immediate Update
```js
cordova.plugins.inappupdate.update("immediate",function(){},function(){});
```

# Support Platform
- Android