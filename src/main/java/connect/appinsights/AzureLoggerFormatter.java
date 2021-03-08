package connect.appinsights;

import connect.logger.ILoggerFormatter;
import connect.models.IdModel;
import connect.util.Collection;
import haxe.root.Array;

public class AzureLoggerFormatter implements ILoggerFormatter {
    private final String currentRequest;

    public AzureLoggerFormatter() {
        currentRequest = "";
    }

    public AzureLoggerFormatter(IdModel request) {
        currentRequest = (request != null) ? request.id + " " : "";
    }

    @Override
    public String formatSection(int level, int sectionLevel, String text) {
        return getSectionPrefix(sectionLevel) + text;
    }

    private String getSectionPrefix(int sectionLevel) {
        if (sectionLevel == 0) {
            return currentRequest;
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < sectionLevel; i++) {
                builder.append('#');
            }
            return currentRequest + builder.toString() + " ";
        }
    }

    @Override
    public String formatBlock(int level, String text) {
        return maybeEol() + text;
    }

    private String maybeEol() {
        return !currentRequest.equals("") ? "\n" : "";
    }

    @Override
    public String formatCodeBlock(int level, String text, String language) {
        return maybeEol() + text;
    }

    @Override
    public String formatList(int level, Collection<String> list) {
        return maybeEol() + list.join("\n");
    }

    @Override
    public String formatTable(int level, Collection<Collection<String>> table) {
        final String[] rows = new String[table.length()];
        for (int i = 0; i < table.length(); i++) {
            rows[i] = "| " + table.get(i).join(" | ") + " |";
        }
        return maybeEol() + String.join("\n", rows);
    }

    @Override
    public String formatLine(int level, String text) {
        return currentRequest + text;
    }

    @Override
    public String getFileExtension() {
        return "log";
    }

    @Override
    public ILoggerFormatter copy(IdModel request) {
        return new AzureLoggerFormatter(request);
    }

    @Override
    public Object __hx_lookupField(String s, boolean b, boolean b1) {
        return null;
    }

    @Override
    public double __hx_lookupField_f(String s, boolean b) {
        return 0;
    }

    @Override
    public Object __hx_lookupSetField(String s, Object o) {
        return null;
    }

    @Override
    public double __hx_lookupSetField_f(String s, double v) {
        return 0;
    }

    @Override
    public double __hx_setField_f(String s, double v, boolean b) {
        return 0;
    }

    @Override
    public Object __hx_setField(String s, Object o, boolean b) {
        return null;
    }

    @Override
    public Object __hx_getField(String s, boolean b, boolean b1, boolean b2) {
        return null;
    }

    @Override
    public double __hx_getField_f(String s, boolean b, boolean b1) {
        return 0;
    }

    @Override
    public Object __hx_invokeField(String s, Object[] objects) {
        return null;
    }

    @Override
    public void __hx_getFields(Array<String> array) {
    }
}
