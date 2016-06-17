angular-decorate
================

Use AngularJS' decorate function with ease.

If you haven't head about decorators in Angular, check this [introduction](http://www.cli-nerd.com/blog/2014/05/08/%24provide-dot-decorator/).

Removes the boilerplate code needed to decorate a service, so that you can decorate **any service** with this simple function call:

```javascript
angular.module(moduleName).decorate(serviceName, decorateFN);
```
## Setup

Load the file `angular-decorate.js` directly after `angular.js`:

```html
<script src="angular.js"></script>
<script src="angular-decorate.js"></script>
```

## Example

Overwrite AngularJS' `templateCache.get()` function to always append `.html`.
```javascript
angular.module('app').decorate('$templateCache', function ($delegate) {
    var _get = $delegate.get;
    $delegate.get = function (key) {
        return _get.call(_get, key + ".html");
    };
    return $delegate;
});

```

## Related
- Introduction: [Angular decorators](http://www.cli-nerd.com/blog/2014/05/08/%24provide-dot-decorator/)
- Talk: [$provide.decorator() - Modifying the behavior of AngularJS' core services](http://www.slideshare.net/damienklinnert/angular-decorate)
- Inspired by [Brian Fords](http://twitter.com/briantford) article [Hacking Core Directives in AngularJS](http://briantford.com/blog/angular-hacking-core.html).
