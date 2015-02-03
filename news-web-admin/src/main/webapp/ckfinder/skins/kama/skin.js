﻿/*
 Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
 For licensing, see LICENSE.html or http://cksource.com/license/ckfinder
 */

CKFINDER.skins.add('kama', (function () {
    var a = ['images/loaders/16x16.gif', 'images/loaders/32x32.gif', 'images/ckffolder.gif', 'images/ckffolderopened.gif'];
    if (CKFINDER.env.ie && CKFINDER.env.version < 7)a.push('images/sprites_ie6.png');
    return {
        preload: a,
        application: {css: ['app.css']},
        host: {intoHostPage: 1, css: ['host.css']},
        marginMainContainer: 7,
        marginSidebarContainer: 7,
        fixMainContentWidth: 1,
        init: function (b) {
            if (b.config.width && !isNaN(b.config.width))b.config.width -= 12;
            var c = [], d = '/* UI Color Support */.cke_skin_kama .cke_menuitem .cke_icon_wrapper{\tbackground-color: $color !important;\tborder-color: $color !important;}.cke_skin_kama .cke_menuitem a:hover .cke_icon_wrapper,.cke_skin_kama .cke_menuitem a:focus .cke_icon_wrapper,.cke_skin_kama .cke_menuitem a:active .cke_icon_wrapper{\tbackground-color: $color !important;\tborder-color: $color !important;}.cke_skin_kama .cke_menuitem a:hover .cke_label,.cke_skin_kama .cke_menuitem a:focus .cke_label,.cke_skin_kama .cke_menuitem a:active .cke_label{\tbackground-color: $color !important;}.cke_skin_kama .cke_menuitem a.cke_disabled:hover .cke_label,.cke_skin_kama .cke_menuitem a.cke_disabled:focus .cke_label,.cke_skin_kama .cke_menuitem a.cke_disabled:active .cke_label{\tbackground-color: transparent !important;}.cke_skin_kama .cke_menuitem a.cke_disabled:hover .cke_icon_wrapper,.cke_skin_kama .cke_menuitem a.cke_disabled:focus .cke_icon_wrapper,.cke_skin_kama .cke_menuitem a.cke_disabled:active .cke_icon_wrapper{\tbackground-color: $color !important;\tborder-color: $color !important;}.cke_skin_kama .cke_menuitem a.cke_disabled .cke_icon_wrapper{\tbackground-color: $color !important;\tborder-color: $color !important;}.cke_skin_kama .cke_menuseparator{\tbackground-color: $color !important;}.cke_skin_kama .cke_menuitem a:hover,.cke_skin_kama .cke_menuitem a:focus,.cke_skin_kama .cke_menuitem a:active{\tbackground-color: $color !important;}';
            if (CKFINDER.env.webkit) {
                d = d.split('}').slice(0, -1);
                for (var e = 0; e < d.length; e++)d[e] = d[e].split('{');
            }
            function f(i) {
                var j = i.getHead().append('style');
                j.setAttribute('id', 'cke_ui_color');
                j.setAttribute('type', 'text/css');
                return j;
            }
            function g(i, j, k) {
                var l, m, n;
                for (var o = 0; o < i.length; o++) {
                    if (CKFINDER.env.webkit) {
                        for (m = 0; m < i[o].$.sheet.rules.length; m++)i[o].$.sheet.removeRule(m);
                        for (m = 0; m < j.length; m++) {
                            n = j[m][1];
                            for (l = 0; l < k.length; l++)n = n.replace(k[l][0], k[l][1]);
                            i[o].$.sheet.addRule(j[m][0], n);
                        }
                    } else {
                        n = j;
                        for (l = 0; l < k.length; l++)n = n.replace(k[l][0], k[l][1]);
                        if (CKFINDER.env.ie)i[o].$.styleSheet.cssText = n;
                        else i[o].setHtml(n);
                    }
                }
            }
            var h = /\$color/g;
            CKFINDER.tools.extend(b, {
                uiColor: null, getUiColor: function () {
                    return this.uiColor;
                }, setUiColor: function (i) {
                    var j, k, l = f(CKFINDER.documentHost), m = f(this.document), n = '.cke_' + b.name.replace('.', '\\.'), o = [n + ' .cke_wrapper', n + '_dialog .cke_dialog_contents', n + '_dialog a.cke_dialog_tab', n + '_dialog .cke_dialog_footer'].join(','), p = 'background-color: $color !important;';
                    if (CKFINDER.env.webkit) {
                        j = [[o, p]];
                        k = [['body,' + o, p]];
                    } else {
                        j = o + '{' + p + '}';
                        k = 'body,' + o + '{' + p + '}';
                    }
                    return (this.setUiColor = function (q) {
                        var r = [[h, q]];
                        b.uiColor = q;
                        g([l], j, r);
                        g([m], k, r);
                        g(c, d, r);
                    })(i);
                }
            });
            b.on('menuShow', function (i) {
                var j = i.data[0], k = j.element.getElementsByTag('iframe').getItem(0).getFrameDocument();
                if (!k.getById('cke_ui_color')) {
                    var l = f(k);
                    c.push(l);
                    var m = b.getUiColor();
                    if (m)g([l], d, [[h, m]]);
                }
            });
            if (b.config.uiColor)b.on('uiReady', function () {
                b.setUiColor(b.config.uiColor);
            });
        }
    };
})());
(function () {
    CKFINDER.dialog ? a() : CKFINDER.on('dialogPluginReady', a);
    function a() {
        CKFINDER.dialog.on('resize', function (b) {
            var c = b.data, d = c.width, e = c.height, f = c.dialog, g = f.parts.contents;
            if (c.skin != 'kama')return;
            g.setStyles({width: d + 'px', height: e + 'px'});
            setTimeout(function () {
                var h = f.parts.dialog.getChild([0, 0, 0]), i = h.getChild(0), j = h.getChild(2);
                j.setStyle('width', i.$.offsetWidth + 'px');
                j = h.getChild(7);
                j.setStyle('width', i.$.offsetWidth - 28 + 'px');
                j = h.getChild(4);
                j.setStyle('height', i.$.offsetHeight - 31 - 14 + 'px');
                j = h.getChild(5);
                j.setStyle('height', i.$.offsetHeight - 31 - 14 + 'px');
            }, 100);
        });
    }
})();
