/**
 * Copyright (c) 2021, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.commons.reporter;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * A <code>Reporter</code> allows building up functional reports with a hierarchy reflecting task/subtasks of processes.
 * The enclosed reports are based on {@link ReportMessage} class.
 *
 * <p>A <code>Reporter</code> can create sub-reporters to separate from current reports the reports from that task.
 * Each sub-reporter is defined by a key identifying the corresponding task, a default <code>String</code> describing
 * the corresponding task, and {@link TypedValue} values indexed by their keys. These values may be referred to
 * by their key in the description of the sub-reporter, or in its reports, using the <code>${key}</code> syntax,
 * in order to be later replaced by {@link org.apache.commons.text.StringSubstitutor} for instance when formatting
 * the string for the end user.
 *
 * <p>Instances of <code>Reporter</code> are not thread-safe.
 * When a new thread is created, a new Reporter should be provided to the process in that thread.
 * A reporter is not meant to be shared with other threads nor to be saved as a class parameter, but should instead
 * be passed on in methods through their arguments.
 *
 * <p>The <code>Reporter</code> can be used for multilingual support. Indeed, each <code>Reporter</code> name and
 * {@link ReportMessage} message can be translated based on their key and using the value keys in the desired order.
 *
 * @author Florian Dupuy <florian.dupuy at rte-france.com>
 */
public interface Reporter {

    /**
     * A No-op implementation of <code>Reporter</code>
     */
    Reporter NO_OP = new NoOpImpl();

    /**
     * Create a sub-reporter for a specific task, to separate from current reports the reports from that task, with
     * some associated values.
     * @param reporterKey the key identifying that sub-reporter
     * @param defaultTitle description of the corresponding task, which may contain references to the provided values
     * @param values a map of {@link TypedValue} indexed by their key, which may be referred to within the defaultTitle
     *               or within the reports message of the created sub-reporter
     * @return the new sub-reporter
     */
    Reporter createSubReporter(String reporterKey, String defaultTitle, Map<String, TypedValue> values);

    /**
     * Create a sub-reporter for a specific task, to separate from current reports the reports from that task, with no
     * associated value.
     * @param reporterKey the key identifying that sub-reporter
     * @param defaultTitle description of the corresponding task
     * @return the new sub-reporter
     */
    Reporter createSubReporter(String reporterKey, String defaultTitle);

    /**
     * Create a sub-reporter for a specific task, to separate from current reports the reports from that task, with one
     * associated value.
     * @param reporterKey the key identifying that sub-reporter
     * @param defaultTitle description of the corresponding task, which may contain references to the provided value
     * @param valueKey the key for the value which follows
     * @param value the value which may be referred to within the defaultTitle or within the reports message of the
     *              created sub-reporter
     * @return the new sub-reporter
     */
    Reporter createSubReporter(String reporterKey, String defaultTitle, String valueKey, Object value);

    /**
     * Create a sub-reporter for a specific task, to separate from current reports the reports from that task, with one
     * associated typed value.
     * @param reporterKey the key identifying that sub-reporter
     * @param defaultTitle description of the corresponding task, which may contain references to the provided typed value
     * @param valueKey the key for the value which follows
     * @param value the value which may be referred to within the defaultTitle or within the reports message of the
     *              created sub-reporter
     * @param type the string representing the type of the value provided
     * @return the new sub-reporter
     */
    Reporter createSubReporter(String reporterKey, String defaultTitle, String valueKey, Object value, String type);

    /**
     * Add a new report message with its associated values.
     * @param messageKey a key identifying the current report message
     * @param defaultMessage the default report message, which may contain references to the provided values or to the
     *                       values of current reporter
     * @param values a map of {@link TypedValue} indexed by their key, which may be referred to within the
     *               defaultMessage provided
     */
    void report(String messageKey, String defaultMessage, Map<String, TypedValue> values);

    /**
     * Add a new report with no associated value.
     * @param messageKey a key identifying the current report
     * @param defaultMessage the default report message, which may contain references to the values of current reporter
     */
    void report(String messageKey, String defaultMessage);

    /**
     * Add a new report with one associated value.
     * @param messageKey a key identifying the current report
     * @param defaultMessage the default report message, which may contain references to the provided value or to the
     *                       values of current reporter
     * @param valueKey the key for the value which follows
     * @param value the int, long, float, double, boolean or String value which may be referred to
     *              within the defaultMessage provided
     */
    void report(String messageKey, String defaultMessage, String valueKey, Object value);

    /**
     * Add a new report with one associated typed value.
     * @param messageKey a key identifying the current report
     * @param defaultMessage the default report message, which may contain references to the provided typed value or to
     *                       the values of current reporter
     * @param valueKey the key for the typed value which follows
     * @param value the int, long, float, double, boolean or String value which may be referred to within the
     *              defaultMessage provided
     * @param type the string representing the type of the value provided
     */
    void report(String messageKey, String defaultMessage, String valueKey, Object value, String type);

    /**
     * Add a new report
     * @param reportMessage the report to add
     */
    void report(ReportMessage reportMessage);

    class NoOpImpl extends AbstractReporter {
        public NoOpImpl() {
            super("noOp", "NoOp", Collections.emptyMap());
        }

        @Override
        public Reporter createSubReporter(String reporterKey, String defaultTitle, Map<String, TypedValue> values) {
            return new NoOpImpl();
        }

        @Override
        public void report(ReportMessage reportMessage) {
            // No-op
        }

        @Override
        public void writeJson(JsonGenerator generator, Map<String, String> dictionary) throws IOException {
            // No-op
        }
    }
}
