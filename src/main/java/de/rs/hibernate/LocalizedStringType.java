/*
 * Copyright (C) 2014  Robert Stark
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package de.rs.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.LocaleType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

public class LocalizedStringType implements CompositeUserType {

	private static final int VALUE = 0;

	private static final int LOCALE = 1;

	@Override
	public String[] getPropertyNames() {
		return new String[] { "value", "locale" };
	}

	@Override
	public Type[] getPropertyTypes() {
		return new Type[] { StringType.INSTANCE, LocaleType.INSTANCE };
	}

	@Override
	public Object getPropertyValue(Object component, int propertyIndex) throws HibernateException {
		if ( component == null ) {
            return null;
        }

        final LocalizedString string = (LocalizedString) component;
        switch ( propertyIndex ) {
            case VALUE: {
                return string.getValue();
            }
            case LOCALE: {
                return string.getLocale();
            }
            default: {
                throw new HibernateException( "Invalid property index [" + propertyIndex + "]" );
            }
        }
	}

	@Override
	public void setPropertyValue(Object component, int propertyIndex, Object value) throws HibernateException {
		if ( component == null ) {
            return;
        }

        final LocalizedString string = (LocalizedString) component;
        switch ( propertyIndex ) {
            case VALUE: {
            	string.setValue( (String) value );
                break;
            }
            case LOCALE: {
            	string.setLocale( (Locale) value );
                break;
            }
            default: {
                throw new HibernateException( "Invalid property index [" + propertyIndex + "]" );
            }
        }
	}

	@Override
	public Class<?> returnedClass() {
		return LocalizedString.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		if (x != null) {
			return x.hashCode();
		}
		return 0;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
//		http://queforum.com/programming-languages-basics/297111-java-usertype-interceptor-handling-i18n-hibernate.html
//		String text = "";
//		String rawId = (String) StringType.INSTANCE.nullSafeGet(rs, names, session, owner);
//		if (rawId != null && rawId.length() > 0) {
//			Long labelId = Long.parseLong(rawId);
//			text = LocalizationLabelUtil.getText(labelId, LocaleContextHolder.getLocale());
//		}
//		return text;
		assert names.length == 2;
        String value = (String) StringType.INSTANCE.nullSafeGet(rs, names[VALUE], session, owner); // already handles null check
        Locale locale = (Locale) LocaleType.INSTANCE.nullSafeGet(rs, names[LOCALE], session, owner); // already handles null check
        return value == null && locale == null
                ? null
                : new LocalizedString( value, locale);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
		if ( value == null ) {
			StringType.INSTANCE.set( st, null, index, session );
			LocaleType.INSTANCE.set( st, null, index+1, session );
        }
        else {
            final LocalizedString string = (LocalizedString) value;
            StringType.INSTANCE.set( st, string.getValue(), index, session );
            LocaleType.INSTANCE.set( st, string.getLocale(), index+1, session );
        }
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(Object value, SessionImplementor session)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object assemble(Serializable cached, SessionImplementor session,
			Object owner) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object replace(Object original, Object target,
			SessionImplementor session, Object owner) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

}
