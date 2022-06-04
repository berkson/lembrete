import { Credentials } from './credentials';

describe('Credentials', () => {
  it('should create an instance', () => {
    expect(new Credentials('João', '123456')).toBeTruthy();
  });
});
