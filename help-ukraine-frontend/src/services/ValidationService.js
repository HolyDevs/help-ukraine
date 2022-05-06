import moment from "moment";

class ValidationService {
    isStringValid = (value) => {
        return value && value.length > 0;
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