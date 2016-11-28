// HideANote - auto hide note thing
// Copyright 2016 by Sarah J. Deer
// All Rights Reserved.
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//

package com.Sarah.HideANote.core;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;

//
// Ideally this should store an xml config file or similar, or perhaps
// go so far as to bundle HSQLDB/Derby/whatever and provide config via
// embedded db. I'm sure there's an appropriate design pattern for this too...
// and I don't want to use properties files.
//

public class Settings {

	private static final Properties _settings = new Properties();

	//
	// initial/default settings
	// 
	static {

		// defaults
		String osName = System.getProperty("os.name").toLowerCase();
		System.out.println("OSNAME:"+osName);

		if (osName.startsWith("windows")) {
			_settings.setProperty("app.dir", System.getProperty("user.home")
					+ File.separator + "HideANote");
		} else {
			_settings.setProperty("app.dir", System.getProperty("user.home")
					+ File.separator + ".HideANote");
		}
		
		new File(_settings.getProperty("app.dir")).mkdir();
		
		_settings.setProperty("stateLock", _settings.getProperty("app.dir") + File.separator + "lock");
		_settings.setProperty("pinLeft", "false");
		_settings.setProperty("pinRight", "true");
		_settings.setProperty("verticalPercentage", "60");
		_settings.setProperty("savedContent", _settings.getProperty("app.dir") + File.separator	+ "content.txt");
		_settings.setProperty("savedSettings", _settings.getProperty("app.dir") + File.separator + "settings.properties");

		// overrides
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(_settings.getProperty("savedSettings")));

			Enumeration pie = properties.propertyNames();

			while (pie.hasMoreElements()) {
				String key = (String) pie.nextElement();
				
				if (_settings.getProperty(key) != null) {
					_settings.setProperty(key, properties.getProperty(key));
				}
			}
		} catch (Exception ignore) {
		} // defaults will just be used
	}

	/**
	 * @param name
	 * @return
	 */
	public static String get(String name) {
		return (_settings.getProperty(name));
	}

	/**
	 * @param name
	 * @param value
	 */
	public static void put(String name, String value) {
		_settings.setProperty(name, value);
		saveSettings();
	}

	private static void saveSettings() {

		try {
			_settings.store(new FileOutputStream(_settings
					.getProperty("savedSettings")), "");
		} catch (IOException ignore) {
		} // can't do anything about it anyway...
	}
}
