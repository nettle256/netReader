/**
 * Created by Nettle on 2017/1/7.
 */

netReader
    .directive('netReaderArticle', function() {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                article: '=article'
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
                article: '=article'
            },
            templateUrl: '/html/novel/translationTemplate',
            link: function (scope, element, attrs) {
            }
        }
    });
