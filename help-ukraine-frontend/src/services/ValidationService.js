import moment from "moment";

class ValidationService {

    #emailPattern = RegExp("(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|" +
        "}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x" +
        "0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:(2(5[0" +
        "-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-zA-Z0-" +
        "9-]*[a-zA-Z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e" +
        "-\\x7f])+)\\])");

    isStringValid = (value) => {
        return value && value.length > 0;
    }

    isEmailValid = (value) => {
        return this.#emailPattern.test(value);
    }

    isDateValid = (date) => {
        const momentDate = moment(date, 'YYYY-MM-DD',true);
        return momentDate.isBefore(moment.now()) && momentDate.isAfter(moment('1900-01-01', 'YYYY-MM-DD'));
    }

    areStringsValid = (values) => {
        for (const value of values) {
            if (!this.isStringValid(value)) {
                return false;
            }
        }
        return true;
    }
}
export default new ValidationService();