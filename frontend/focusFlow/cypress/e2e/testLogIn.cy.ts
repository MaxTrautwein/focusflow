describe('template spec', () => {
  //everything after it in {} is one tests. In '' there is the name of the test
  it('passes', () => {
    cy.visit('http://localhost:5173/')
  })

  it('check buttons exist right', () => {
    cy.visit('http://localhost:5173/signIn')
    cy.get('[data-cy=singIn-button]')
      .should('exist')
      .should('have.text', 'Sign in')
    cy.get('[data-cy=signUp-button]')
      .should('exist')
      .should('have.text', 'Sign Up')
  })

  it('check signUp button is workin', () => {
    cy.visit('http://localhost:5173/signIn')
    cy.get('[data-cy=signUp-button]').click()
    cy.url().should('include', '/signUp')
  })

  it('check no input in text fields', () => {
    cy.visit('http://localhost:5173/signIn')
    cy.get('[data-cy=singIn-button]').click()
  })

  it('check user doesnt exist  input in text fields', () => {
    cy.visit('http://localhost:5173/signIn')
    cy.get('[data-cy=email-input]').type('test@mail.com')
    cy.get('[data-cy=password-input]').type('test!1234AA')
    cy.get('[data-cy=singIn-button]').click()
  })

  it('check just one input in text fields, email', () => {
    cy.visit('http://localhost:5173/signIn')
    cy.get('[data-cy=email-input]').type('test@mail.com')
    cy.get('[data-cy=singIn-button]').click()
  })

  it('check just one input in text fields, password', () => {
    cy.visit('http://localhost:5173/signIn')
    cy.get('[data-cy=password-input]').type('test!1234AA')
    cy.get('[data-cy=singIn-button]').click()
  })

  it('sign up user', () => {
    cy.visit('http://localhost:5173/signUp')
    cy.get('[data-cy=firstName-input-signUpPage]').type(
      `user_${Date.now()}TestName`
    )
    cy.get('[data-cy=lastName-input-signUpPage]').type(
      `user_${Date.now()}TestLastName`
    )
    cy.get('[data-cy=email-input-signUpPage]').type(
      `user_${Date.now()}fake@mail.de`
    )
    cy.get('[data-cy=password-input-signUpPage]').type('test!4564AA')
    cy.get('[data-cy=signUp-button-signUpPage]').click()
  })

  it('password to long', () => {
    cy.visit('http://localhost:5173/signUp')
    cy.get('[data-cy=firstName-input-signUpPage]').type(
      `user_${Date.now()}TestName`
    )
    cy.get('[data-cy=lastName-input-signUpPage]').type(
      `user_${Date.now()}TestLastName`
    )
    cy.get('[data-cy=email-input-signUpPage]').type(
      `user_${Date.now()}fake@mail.de`
    )
    cy.get('[data-cy=password-input-signUpPage]').type('test!4564AAAA')
    cy.get('[data-cy=signUp-button-signUpPage]').click()
  })

  it('password to short', () => {
    cy.visit('http://localhost:5173/signUp')
    cy.get('[data-cy=firstName-input-signUpPage]').type(
      `user_${Date.now()}TestName`
    )
    cy.get('[data-cy=lastName-input-signUpPage]').type(
      `user_${Date.now()}TestLastName`
    )
    cy.get('[data-cy=email-input-signUpPage]').type(
      `user_${Date.now()}fake@mail.de`
    )
    cy.get('[data-cy=password-input-signUpPage]').type('test!4564')
    cy.get('[data-cy=signUp-button-signUpPage]').click()
  })

  it('password missing capital letter', () => {
    cy.visit('http://localhost:5173/signUp')
    cy.get('[data-cy=firstName-input-signUpPage]').type(
      `user_${Date.now()}TestName`
    )
    cy.get('[data-cy=lastName-input-signUpPage]').type(
      `user_${Date.now()}TestLastName`
    )
    cy.get('[data-cy=email-input-signUpPage]').type(
      `user_${Date.now()}fake@mail.de`
    )
    cy.get('[data-cy=password-input-signUpPage]').type('test!4564aa')
    cy.get('[data-cy=signUp-button-signUpPage]').click()
  })

  it('password missing lowercase letter', () => {
    cy.visit('http://localhost:5173/signUp')
    cy.get('[data-cy=firstName-input-signUpPage]').type(
      `user_${Date.now()}TestName`
    )
    cy.get('[data-cy=lastName-input-signUpPage]').type(
      `user_${Date.now()}TestLastName`
    )
    cy.get('[data-cy=email-input-signUpPage]').type(
      `user_${Date.now()}fake@mail.de`
    )
    cy.get('[data-cy=password-input-signUpPage]').type('TEST!4564AA')
    cy.get('[data-cy=signUp-button-signUpPage]').click()
  })

  it('password missing number', () => {
    cy.visit('http://localhost:5173/signUp')
    cy.get('[data-cy=firstName-input-signUpPage]').type(
      `user_${Date.now()}TestName`
    )
    cy.get('[data-cy=lastName-input-signUpPage]').type(
      `user_${Date.now()}TestLastName`
    )
    cy.get('[data-cy=email-input-signUpPage]').type(
      `user_${Date.now()}fake@mail.de`
    )
    cy.get('[data-cy=password-input-signUpPage]').type('test!bbbbAA')
    cy.get('[data-cy=signUp-button-signUpPage]').click()
  })

  it('password special character', () => {
    cy.visit('http://localhost:5173/signUp')
    cy.get('[data-cy=firstName-input-signUpPage]').type(
      `user_${Date.now()}TestName`
    )
    cy.get('[data-cy=lastName-input-signUpPage]').type(
      `user_${Date.now()}TestLastName`
    )
    cy.get('[data-cy=email-input-signUpPage]').type(
      `user_${Date.now()}fake@mail.de`
    )
    cy.get('[data-cy=password-input-signUpPage]').type('test14564AA')
    cy.get('[data-cy=signUp-button-signUpPage]').click()
  })

  it('create user and log in', () => {
    cy.visit('http://localhost:5173/signUp')
    cy.get('[data-cy=firstName-input-signUpPage]').type('name')
    cy.get('[data-cy=lastName-input-signUpPage]').type('lastname')
    cy.get('[data-cy=email-input-signUpPage]').type('real@mail.de')
    cy.get('[data-cy=password-input-signUpPage]').type('test!123AA')
    cy.get('[data-cy=signUp-button-signUpPage]').click()
  })

  it('log in with user', () => {
    cy.visit('http://localhost:5173/signIn')
    cy.get('[data-cy=email-input]').type('real@mail.de')
    cy.get('[data-cy=password-input]').type('test!123AA')
    cy.get('[data-cy=singIn-button]').click()
    cy.url().should('include', '/dashboard')
    cy.get('[data-cy=navbar-logout-button]').should('exist').click()
    cy.url().should('include', '/signIn')
  })
})
