class LabelService {
    #labels = new Map([
        ["FEMALE", "Female"],
        ["MALE", "Male"]
    ]);

    getLabelFromKey(key) {
        if (!this.#labels.has(key)) {
            const msg = "No label associated with key '" + key + "'";
            console.error(msg);
            throw new Error(msg);
        }
        return this.#labels.get(key);
    }

    getKeyFromLabel(label) {
        for (const [key, value] of this.#labels.entries()) {
            if (label === value) {
                return key;
            }
        }
        const msg = "No key associated with label '" + label + "'";
        console.error(msg);
        throw new Error(msg);
    }
}

export default new LabelService();