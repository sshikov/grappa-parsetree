package com.github.fge.grappa.parsetree;

public abstract class ParseNode
{
    protected final String value;

    protected ParseNode(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return value;
    }
}
