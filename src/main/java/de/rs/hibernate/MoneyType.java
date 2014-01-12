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
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.CurrencyType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

public class MoneyType implements CompositeUserType {

	@Override
	public String[] getPropertyNames() {
		// ORDER IS IMPORTANT!  it must match the order the columns are defined in the property mapping
        return new String[] { "amount", "currency" };
	}

	@Override
	public Type[] getPropertyTypes() {
		return new Type[] { BigDecimalType.INSTANCE, CurrencyType.INSTANCE };
	}

	@Override
	public Object getPropertyValue(Object component, int propertyIndex) throws HibernateException {
		if ( component == null ) {
            return null;
        }

        final Money money = (Money) component;
        switch ( propertyIndex ) {
            case 0: {
                return money.getAmount();
            }
            case 1: {
                return money.getCurrency();
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

        final Money money = (Money) component;
        switch ( propertyIndex ) {
            case 0: {
                money.setAmount( (BigDecimal) value );
                break;
            }
            case 1: {
                money.setCurrency( (Currency) value );
                break;
            }
            default: {
                throw new HibernateException( "Invalid property index [" + propertyIndex + "]" );
            }
        }
	}

	@Override
	public Class<?> returnedClass() {
		return Money.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
		assert names.length == 2;
        BigDecimal amount = BigDecimalType.INSTANCE.fromString( names[0] ); // already handles null check
        Currency currency = CurrencyType.INSTANCE.fromString( names[1] ); // already handles null check
        return amount == null && currency == null
                ? null
                : new Money( amount, currency );
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
		if ( value == null ) {
            BigDecimalType.INSTANCE.set( st, null, index, session );
            CurrencyType.INSTANCE.set( st, null, index+1, session );
        }
        else {
            final Money money = (Money) value;
            BigDecimalType.INSTANCE.set( st, money.getAmount(), index, session );
            CurrencyType.INSTANCE.set( st, money.getCurrency(), index+1, session );
        }
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.CompositeUserType#deepCopy(java.lang.Object)
	 */
	@Override
	public Object deepCopy(Object value) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.CompositeUserType#isMutable()
	 */
	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.CompositeUserType#disassemble(java.lang.Object, org.hibernate.engine.spi.SessionImplementor)
	 */
	@Override
	public Serializable disassemble(Object value, SessionImplementor session)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.CompositeUserType#assemble(java.io.Serializable, org.hibernate.engine.spi.SessionImplementor, java.lang.Object)
	 */
	@Override
	public Object assemble(Serializable cached, SessionImplementor session,
			Object owner) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.CompositeUserType#replace(java.lang.Object, java.lang.Object, org.hibernate.engine.spi.SessionImplementor, java.lang.Object)
	 */
	@Override
	public Object replace(Object original, Object target,
			SessionImplementor session, Object owner) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

}
