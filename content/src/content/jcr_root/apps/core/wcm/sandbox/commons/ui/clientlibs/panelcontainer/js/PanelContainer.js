/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2018 Adobe Systems Incorporated
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/* global CQ */
(function($, ns, channel, window, undefined) {
    "use strict";

    var GET_DATA_SUFFIX = ".model.json";
    var POST_SUFFIX = ".children.html";

    /**
     * @typedef {Object} PanelContainerConfig Represents a Panel Container configuration object
     * @property {String} path The path to the Panel Container component represented
     * @property {PanelContainerType} [panelContainerType] The Panel Container Type definition
     * @property {HTMLElement} [el] The HTMLElement in the Granite.author.ContentFrame related to this panel container
     */

    /**
     * @class CQ.CoreComponents.PanelContainer
     * @classdesc A Panel Container relates to a component concept whereby child items (panels) are hidden/shown. This Class provides
     * operations that allow editing functionality, including the ability to navigate to panels for editing,
     * fetching panel items, determining the active panel and persisting updates to the server.
     * @param {PanelContainerConfig} config The Panel Container configuration object
     */
    CQ.CoreComponents.PanelContainer = ns.util.createClass({

        /**
         * The Panel Container Object
         *
         * @member {PanelContainerConfig} CQ.CoreComponents.PanelContainer#_config
         */
        _config: {},

        /**
         * The data Object retrieved from an endpoint for this Panel Container that represents its properties
         * and child items
         *
         * @member {Object} CQ.CoreComponents.PanelContainer#_data
         */
        _data: {},

        constructor: function PanelContainer(config) {
            this._config = config;
            this.getItems();
        },

        /**
         * Navigates to the panel at the provided index. Posts a message to the
         * [Content Frame]{@link Granite.author.ContentFrame} and lets the related UI widget handle the operation.
         *
         * @param {Number} index Index of the panel to navigate to
         */
        navigate: function(index) {
            if (this._config.panelContainerType) {
                Granite.author.ContentFrame.postMessage(this._config.panelContainerType.name, { panel: index });
            }
        },

        /**
         * Gets the index of the currently active panel
         *
         * @returns {Object} The panel container items data
         */
        getActiveIndex: function() {
            var that = this;
            var activeIndex = 0;
            if (that._config.el && that._config.panelContainerType) {
                var items = $(this._config.el).find(that._config.panelContainerType.itemSelector);
                items.each(function(index) {
                    if ($(this).is(that._config.panelContainerType.itemActiveSelector)) {
                        activeIndex = index;
                        return false;
                    }
                });
            }
            return activeIndex;
        },

        /**
         * Gets the items data for this panel container
         *
         * @returns {Promise.<Array.<*>>} A promise for handling completion, with items as resolved values
         */
        getItems: function() {
            var that = this;
            var deferred = $.Deferred();

            if (that._data.items) {
                // data is already cached, don't re-fetch
                deferred.resolve(that._data.items);
            }

            $.ajax({
                url: that._config.path + GET_DATA_SUFFIX
            }).done(function(data) {
                if (data) {
                    that._data = data;
                }
                deferred.resolve(that._data.items);
            }).fail(function() {
                deferred.resolve([]);
            });

            return deferred.promise();
        },

        /**
         * Returns the path to the component represented by this panel container
         *
         * @returns {String} The path of this panel container
         */
        getPath: function() {
            return this._config.path;
        },

        /**
         * Persists item updates to an endpoint, returns a Promise for handling
         *
         * @param {Array} ordered IDs of the items in order
         * @param {Array} [deleted] IDs of the deleted items
         * @returns {Promise} The promise for completion handling
         */
        update: function(ordered, deleted) {
            var url = this._config.path + POST_SUFFIX;

            return $.ajax({
                type: "POST",
                url: url,
                data: {
                    "deletedChildren": deleted,
                    "orderedChildren": ordered
                }
            });
        }
    });

}(jQuery, Granite.author, jQuery(document), this));
