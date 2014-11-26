/**
 * 
 */
package org.finnex.mdiary.diary;

/**
 * @author finneon
 *
 */
public class ValidatorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ValidatorException() {
	}

	/**
	 * @param arg0
	 */
	public ValidatorException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ValidatorException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ValidatorException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
