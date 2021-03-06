package io.github.syst3ms.skriptparser.registration.tags;

import io.github.syst3ms.skriptparser.lang.Expression;
import io.github.syst3ms.skriptparser.lang.SkriptEvent;
import io.github.syst3ms.skriptparser.lang.SyntaxElement;
import io.github.syst3ms.skriptparser.registration.SkriptAddon;

/**
 * A class containing info about a {@link SyntaxElement} that isn't an {@link Expression} or an {@link SkriptEvent}
 * @param <C> the {@link SyntaxElement} class
 * @author Mwexim
 */
public class TagInfo<C extends Tag> {
	private final Class<C> c;
	private final int priority;
	private final SkriptAddon registerer;

	public TagInfo(Class<C> c, int priority, SkriptAddon registerer) {
		this.c = c;
		this.priority = priority;
		this.registerer = registerer;
	}

	public Class<C> getSyntaxClass() {
		return c;
	}

	public int getPriority() {
		return priority;
	}

	public SkriptAddon getRegisterer() {
		return registerer;
	}
}
