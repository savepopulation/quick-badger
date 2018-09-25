# quick-badger
A library to show notification badges on app launchers which targets lower Android Sdks

## How to?
To show a badge on launcher:
```
QuickBadger.provideBadger(this)?.showBadge( 10)
```

To dismiss badge
```
QuickBadger.provideBadger(this)?.dismissBadge()
```
## Dependency
<b>Step 1:</b> Add it in your root build.gradle at the end of repositories.

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

<b>Step 2:</b> Add the dependency.

```
	dependencies {
	        implementation 'com.github.savepopulation:quick-badger:1.0.0'
	}
```


