using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using DeveloperTest;

namespace DeveloperTestRunner
{
    internal interface ITests
    {
        IDeveloperTest Implementation { get; set; }
        QuestionTester Tester { get; set; }
    }

    internal abstract class Tests : ITests
    {
        public IDeveloperTest Implementation { get; set; }
        public QuestionTester Tester { get; set; }
    }
}
