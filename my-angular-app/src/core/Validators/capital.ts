import { AbstractControl } from "@angular/forms";

export function checkForCapital(control: AbstractControl): { [key: string]: boolean } | null {
    const value = control.value as string;
    const hasCapital = /[A-Z]/.test(value);
    return hasCapital ? null : { noCapital: true };
}