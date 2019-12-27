# quick-badger
A library to show notification badges on app launchers which targets lower Android Sdks

## How to?
To show a badge on launcher first add device badger classes that you want to support:
```
QuickBadger.withBadgers(SamsungBadger::class)
                .provideBadger(this)?.showBadge(5)```

To dismiss badge
```
QuickBadger.provideBadger(this)?.dismissBadge()
```

To use your custom Badger implementations
```
QuickBadger.withBadgers(MyCustomBadger::class)
           .provideBadger(this)?
	   .showBadge(5)
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
	       	implementation 'com.github.savepopulation:quick-badger:1.0.3'
	}
```


