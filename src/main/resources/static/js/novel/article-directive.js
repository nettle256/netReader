/**
 * Created by Nettle on 2017/1/7.
 */

netReader
    .directive('netReaderArticle', function() {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                article: '=article',
                author: '=author'
            },
            templateUrl: '/html/novel/articleTemplate',
            link: function (scope, element, attrs) {
            }
        }
    })
    .directive('netReaderTranslation', function() {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                translation: '=translation',
                original: '=original'
            },
            templateUrl: '/html/novel/translationTemplate',
            link: function (scope, element, attrs) {
            }
        }
    });
