/**
 *   This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Copyright 2011-2017 Peter Güttinger and contributors
 */
package ch.njol.skript.tests.runner;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;

public class EvtTestCase extends SkriptEvent {
	
	static {
		if (TestMode.ENABLED)
			Skript.registerEvent("Test Case", EvtTestCase.class, SkriptTestEvent.class, "test %string%")
				.description("Contents represent one test case.")
				.examples("")
				.since("INSERT VERSION");
	}
	
	@SuppressWarnings("null")
	private Expression<String> name;
	
	@SuppressWarnings({"null", "unchecked"})
	@Override
	public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
		name = (Expression<String>) args[0];
		return true;
	}
	
	@Override
	public boolean check(Event e) {
		String n = name.getSingle(e);
		if (n == null) {
			return false;
		}
		Skript.info("Running test case " + n);
		TestTracker.testStarted(n);
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		if (e != null)
			return "test " + name.getSingle(e);
		return "test case";
	}
}
